package pomf.api.route

import akka.actor.{ ActorRef, ActorContext }
import akka.pattern._
import akka.http.model.StatusCodes._
import akka.http.marshalling.Marshaller._
import akka.http.marshalling.ToResponseMarshallable
import akka.http.marshallers.sprayjson.SprayJsonSupport._
import akka.http.marshalling.ToResponseMarshallable._
import akka.http.server._
import Directives._
import akka.stream.FlowMaterializer
import pomf.service.CrudService

import spray.json._
import spray.json.DefaultJsonProtocol._

import pomf.core.metrics.MetricsReporterProtocol
import pomf.core.metrics.MetricsReporterProtocol._
import pomf.configuration._

object StatsRoute {

  def build(crudService: CrudService, metricsRepo: ActorRef)(implicit context: ActorContext, fm: FlowMaterializer) = {
    implicit val timeout = akka.util.Timeout(Settings(context.system).Timeout)
    implicit val ec = context.dispatcher

    path("stats") {
      get {
        complete {
          (metricsRepo ? MetricsReporterProtocol.All).mapTo[MetricsReport].map {
            case MetricsReport(metrics) ⇒ ToResponseMarshallable(OK -> metrics)
          }
        }
      }
    } ~
      pathPrefix("count") {
        path("fridges") {
          get {
            onSuccess(crudService.countFridges()) { nb: Int ⇒
              complete(ToResponseMarshallable(OK -> nb))
            }
          }
        } ~
          path("posts") {
            get {
              onSuccess(crudService.countPosts()) { nb: Int ⇒
                complete(ToResponseMarshallable(OK -> nb))
              }
            }
          }
      }
  }
}
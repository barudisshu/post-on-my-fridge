package pomf.api.request

import akka.actor._
import akka.pattern._

import spray.httpx.SprayJsonSupport._
import spray.routing._
import spray.json._

import DefaultJsonProtocol._

import pomf.metrics.MetricsReporterProtocol._
import pomf.metrics.MetricsReporterProtocol

class AllMetrics(ctx : RequestContext, metricsRepo: ActorRef)(implicit breaker: CircuitBreaker) extends RestRequest(ctx) {
  metricsRepo ! MetricsReporterProtocol.All

  override def receive = super.receive orElse waitingMetrics

  def waitingMetrics : Receive = {
    case MetricsReport(metrics) => requestOver(metrics)
  }
}

object AllMetrics {
   def props(ctx : RequestContext, metricsRepo: ActorRef)(implicit breaker: CircuitBreaker) 
     = Props(classOf[AllMetrics], ctx, metricsRepo, breaker).withDispatcher("requests-dispatcher")
}
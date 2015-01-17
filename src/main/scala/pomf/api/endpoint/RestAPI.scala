package pomf.api.endpoint

import akka.actor._
import akka.http.Http
import akka.http.coding.Gzip
import akka.http.server.Directives._
import akka.http.server.RoutingSettings._
import akka.stream.FlowMaterializer
import pomf.api.exceptions.RestFailureHandler
import pomf.api.route._
import pomf.configuration.Settings
import pomf.core.CoreActors
import pomf.core.actors.CommonActor

class RestAPI(coreActors: CoreActors, system: ActorSystem, fm: FlowMaterializer)
    extends CommonActor
    with RestFailureHandler {

  implicit val executionContext = system.dispatcher
  implicit val ifm = fm

  override def receive: Receive = Actor.emptyBehavior

  import coreActors._

  val chat = ChatRoute.build(chatRepo)
  val files = FilesRoute.build
  val fridge = FridgeRoute.build(crudService)
  val post = PostRoute.build(crudService)
  val search = SearchRoute.build(crudService)
  val stats = StatsRoute.build(crudService, metricsReporter)
  val streaming = StreamingRoute.build
  val token = TokenRoute.build()

  val routes = encodeResponse(Gzip) {
    chat ~ files ~ fridge ~ post ~ search ~ stats ~ streaming ~ token
  }

  Http(system)
    .bind(interface = "localhost", port = Settings(system).Http.Port)
    .startHandlingWith(routes)
}

object RestAPI {
  def props(coreActors: CoreActors)(implicit system: ActorSystem, fm: FlowMaterializer) =
    Props(classOf[RestAPI], coreActors, system, fm)
}
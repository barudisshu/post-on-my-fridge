package pomf.api.endpoint

import akka.actor._

import spray.routing._

import scala.concurrent.duration._
import scala.concurrent.Future

import pomf.api.route._
import pomf.api.exceptions.RestFailureHandling
import pomf.core.CoreActors
import pomf.metrics.MetricsReporter

class HttpEndpointActor(coreActors : CoreActors) extends HttpEndpoint with Actor {
  implicit def actorRefFactory = context    
  def receive = runRoute(routes(coreActors))
}

trait HttpEndpoint extends HttpService with RestFailureHandling {
 
  def routes(coreActors : CoreActors ) (implicit context: ActorContext) = {
    val crudService = coreActors.crudService
    val chatService = coreActors.chatService
    val tokenService = coreActors.tokenService

    val chat = new ChatRoute(chatService).route
    val files = new FilesRoute().route 
    val fridge = new FridgeRoute(crudService).route    
    val post = new PostRoute(crudService).route    
    val search = new SearchRoute(crudService).route   
    val stats = new StatsRoute(crudService).route
    val streaming = new StreamingRoute().route     
    val token = new TokenRoute(tokenService).route             

    chat ~ files ~ fridge ~ post ~ search ~ stats ~ streaming ~ token 
  } 
}

object HttpEndpointActor {
  def props(coreActors : CoreActors) = Props(classOf[HttpEndpointActor], coreActors)
}
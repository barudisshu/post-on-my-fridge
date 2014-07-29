package pomf.api.route

import akka.actor._

import spray.routing._

import pomf.api.request.{AllMetrics, CountFridges, CountPosts}

class StatsRoute(crudService : ActorRef, metricsRepo : ActorRef)(implicit context: ActorContext) extends RouteWithBreaker {

  val route = 
    path("stats") {
      get { ctx =>
        context.actorOf(AllMetrics.props(ctx, metricsRepo))
      }
    } ~ 
    pathPrefix("count") {
      path("fridges") {
          get { ctx =>
            context.actorOf(CountFridges.props(ctx, crudService))
          }
      } ~ 
      path("posts") {
          get { ctx =>
            context.actorOf(CountPosts.props(ctx, crudService))
          }
      }
    }        
}
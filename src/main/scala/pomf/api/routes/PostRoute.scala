package pomf.api.route

import akka.actor.ActorContext
import akka.http.marshalling.ToResponseMarshallable
import akka.http.model.StatusCodes._
import akka.http.marshalling.Marshaller._
import akka.http.server._
import Directives._
import akka.stream.FlowMaterializer

import java.util.UUID
import pomf.api.endpoint.JsonSupport
import pomf.domain.CrudService
import pomf.domain.model.Post
import pomf.configuration._

object PostRoute extends JsonSupport {

  def build(crudService: CrudService)(implicit context: ActorContext, fm: FlowMaterializer) = {
    implicit val timeout = akka.util.Timeout(Settings(context.system).Timeout)
    implicit val ec = context.dispatcher

    path("posts") {
      post {
        parameters("token") { token: String ⇒
          entity(as[Post]) { post: Post ⇒
            onSuccess(crudService.addPost(post, token)) { created: Post ⇒
              complete(ToResponseMarshallable(OK -> created))
            }
          }
        }
      } ~
        put {
          parameters("token") { token: String ⇒
            entity(as[Post]) { post: Post ⇒
              onSuccess(crudService.updatePost(post, token)) { updated: Post ⇒
                complete(ToResponseMarshallable(OK -> updated))
              }
            }
          }
        }
    } ~
      path("posts" / JavaUUID) { postId: UUID ⇒
        get {
          onSuccess(crudService.getPost(postId)) { post: Post ⇒
            complete(ToResponseMarshallable(OK -> post))
          }
        } ~
          delete {
            parameters("token") { token: String ⇒
              onSuccess(crudService.deletePost(postId, token)) { msg: String ⇒
                complete(ToResponseMarshallable(OK -> msg))
              }
            }
          }
      }
  }
}
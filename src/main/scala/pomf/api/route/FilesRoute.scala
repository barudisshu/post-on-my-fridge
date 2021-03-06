package pomf.api.route

import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.model.headers.CacheDirectives._
import akka.http.scaladsl.server._
import Directives._

object FilesRoute {

  val build =
    pathSingleSlash {
      respondWithHeader(`Cache-Control`(`public`, `max-age`(60L * 60L * 24L * 31L))) {
        getFromResource("frontend/web/dist/index.html")
      }
    } ~
      respondWithHeader(`Cache-Control`(`public`, `max-age`(60L * 60L * 24L * 31L))) {
        getFromResourceDirectory("frontend/web/dist")
      }
}
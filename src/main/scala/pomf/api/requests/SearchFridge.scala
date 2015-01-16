package pomf.api.request

import akka.actor.{ Actor, ActorRef, Props }
import akka.http.server._
import akka.http.marshallers.sprayjson.SprayJsonSupport._

import spray.json._
import DefaultJsonProtocol._

import pomf.api.endpoint.JsonSupport._
import pomf.service.CrudServiceProtocol._
import pomf.service.CrudServiceProtocol

class SearchFridge(term: String, ctx: RequestContext, crudService: ActorRef) extends RestRequest(ctx) {

  crudService ! CrudServiceProtocol.SearchFridge(term)

  override def receive = super.receive orElse waitingSearch

  def waitingSearch: Receive = {
    case SearchResult(t, r) ⇒ requestOver(r)
  }
}

object SearchFridge {
  def props(term: String, ctx: RequestContext, crudService: ActorRef) = Props(classOf[SearchFridge], term, ctx, crudService)
}
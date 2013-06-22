package pomf.service

import pomf.api.JsonSupport._
import pomf.Boot
import pomf.domain.config._
import pomf.domain.model._
import pomf.service.CrudServiceActor._

import akka.actor._
import akka.pattern._
import akka.event.LoggingReceive
import akka.util.Timeout

import scala.concurrent._

import com.redis.serialization.Parse

class CrudServiceActor extends Actor with ActorLogging with PomfCachingService with ProductionDB { 
  this: DBConfig =>
  import Parse.Implicits._

  implicit def executionContext = context.dispatcher
  
  private var notificationService : ActorRef = _

  override def preStart() {
     notificationService = context.actorFor("/user/notification-service")
  }

  def receive = LoggingReceive {
      case FullFridge(fridgeName)               => sender ! getFridgeRest(fridgeName)
      case AllFridge()                          => sender ! getAllFridge()
      case CreateFridge(fridge)                 => sender ! addFridge(fridge)
      case GetPost(postId)                      => sender ! getPost(postId)
      case DeletePost(postId, token)            => sender ! deletePost(postId, token)
      case CreatePost(post, token)              => sender ! addPost(post, token)
      case UpdatePost(post, token)              => sender ! updatePost(post, token)
      case FridgeRss(fridgeName)                => sender ! getFridgeRss(fridgeName)
      case SearchFridge(term)                   => sender ! searchByNameLike(term)
      case PushChat(fridgeName, message, token) => sender ! addChatMessage(fridgeName, message, token)
      case ChatHistory(fridgeName)              => sender ! retrieveChatHistory(fridgeName)
  }

  def getAllFridge(): List[Fridge] = dao.getAllFridge

  def addFridge(fridge: Fridge): Fridge = dao.addFridge(fridge)

  def addPost(post: Post, token: String): Post = {
    val persistedPost = dao.addPost(post)
    notificationService ! Notifications.create(post.fridgeId, persistedPost, token)
    persistedPost
  }

  def getFridgeRest(fridgeName: String): FridgeRest = {
      log.debug("Requesting fridge {}", fridgeName)
      dao.getFridgeRest(fridgeName)
  }

  def getFridgeRss(fridgeName: String): scala.xml.Elem = dao.getFridgeRss(fridgeName)

  def getPost(id: Long): Option[Post] = dao.getPost(id)

  def searchByNameLike(term: String): List[String] = dao.searchByNameLike(term)

  def deletePost(id: Long, token: String): String = {
    notificationService ! Notifications.delete(getPost(id).get.fridgeId, id, token)
    "post " + dao.deletePost(id) + " deleted"
  }

  def updatePost(post: Post, token: String): Post = {
    notificationService ! Notifications.update(post.fridgeId, post, token)
    dao.updatePost(post).orNull
  }

  def addChatMessage(fridgeName: String, message: ChatMessage, token: String): ChatMessage = {
    //cache.lpush(fridgeName+".chat", message)
    notificationService ! Notifications.message(fridgeName, message, token)
    message
  }

  def retrieveChatHistory(fridgeName: String): List[ChatMessage] = {
    //cache.get[List[ChatMessage]](fridgeName+".chat")
    List()
  }
}

object CrudServiceActor {
  case class FullFridge(fridgeName : String)
  case class AllFridge()
  case class CreateFridge(fridge : Fridge) 
  case class GetPost(postId : Long)
  case class UpdatePost(post: Post, token: String)
  case class CreatePost(post: Post, token: String)
  case class DeletePost(postId: Long, token: String)
  case class FridgeRss(fridgeName: String)
  case class SearchFridge(term: String)
  case class PushChat(fridgeName: String, message: ChatMessage, token: String)
  case class ChatHistory(fridgeName: String)
}
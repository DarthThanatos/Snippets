package shop

import akka.actor.{Actor, ActorLogging}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import communication.{Item}
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import akka.pattern.pipe
import akka.util.ByteString

class PaymentService(items: List[Item]) extends Actor with ActorLogging{

  import context.dispatcher

  val http = Http(context.system)
  final implicit val materializer: ActorMaterializer =
    ActorMaterializer(ActorMaterializerSettings(context.system))

  override def preStart(): Unit = {
  }

//  case class Payment(amount: Float) extends RequestEntity
  override def receive :Receive = {

    case DoPayment =>
      println("Payment service got dopayment order: " )
      items foreach println
      val sum = items.map(_.price).sum
      http.singleRequest(HttpRequest(method = HttpMethods.POST, uri = "http://192.168.0.100/payment", entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, sum.toString))).pipeTo(self)

    case HttpResponse(StatusCodes.OK, headers, entity, _) =>
      entity.dataBytes.runFold(ByteString(""))(_ ++ _).foreach { body =>
        log.info("Got response, body: " + body.utf8String)
      }
      context.actorSelection("/user/customer") ! PaymentConfirmed
      val sum = items.map(_.price).sum
      context.parent ! ReceivePayment(sum.toFloat)
      context stop self

    case resp @ HttpResponse(code, _, _, _) =>
      log.info("Request failed, response code: " + code)
      resp.discardEntityBytes()

  }

}

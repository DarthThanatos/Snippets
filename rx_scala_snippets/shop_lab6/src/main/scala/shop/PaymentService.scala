package shop

import akka.actor.{Actor, ActorLogging}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import communication.{HttpException, Item}
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import akka.pattern.pipe
import akka.util.ByteString

import scala.concurrent.duration._

import scala.concurrent.Await

class PaymentService(items: List[Item]) extends Actor with ActorLogging{

  import context.dispatcher

  val http = Http(context.system)
  final implicit val materializer: ActorMaterializer =
    ActorMaterializer(ActorMaterializerSettings(context.system))

  private def correctRequest(sum: Float) =
    HttpRequest(
      method = HttpMethods.POST,
      uri = "http://192.168.0.100/payment",
      entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, sum.toString)
    )


  private def stringFromFuture(entity: HttpEntity): String =
    Await.result(
      entity.dataBytes.runFold(ByteString(""))(_ ++ _),
      1.minutes
    ).utf8String


  override def preStart(): Unit = {
    super.preStart()
    println("Started paypaltestclient")
  }

  override def receive :Receive = {

    case PerformRequest(request) =>
      println("Payment service got request to perform: " + request)
      http.singleRequest(request).pipeTo(self)


    case DoPayment =>
      println("Payment service got dopayment order: " )
      items foreach println
      val sum = items.map(_.price).sum
      http.singleRequest(correctRequest(sum.toFloat)).pipeTo(self)

    case HttpResponse(StatusCodes.OK, headers, entity, _) =>
      entity.dataBytes.runFold(ByteString(""))(_ ++ _).foreach { body =>
        log.info("Got response, body: " + body.utf8String)
      }

      context.actorSelection("/user/customer") ! PaymentConfirmed
      val sum = items.map(_.price).sum
      context.parent ! ReceivePayment(sum.toFloat)
      context stop self

    case resp @ HttpResponse(code, _, entity, _) if code.intValue() == 400 =>
      resp.discardEntityBytes()
      val responseText = stringFromFuture(entity)
      throw new IllegalArgumentException(responseText)

    case resp @ HttpResponse(code, _, entity, _) =>
      log.info("Request failed, response code: " + code)
      resp.discardEntityBytes()
      throw HttpException(code.intValue(), entity.toString)

  }

}

/*
  errors handled:
  - what if a server is not set up(connection refused due to an improper port binding, the connection timeout)
  - what if payload is incorrect (expected string parsable to float, got sth not parsable instead)
  - surviving situations of http exceptions thrown by hardcoded endpoints
 */

package paymentServer

import java.net.URI

import akka.actor.{Actor, ActorSystem, OneForOneStrategy, Props}
import communication.{HttpException, Item}
import akka.actor.SupervisorStrategy._
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethods, HttpRequest}
import com.typesafe.config.ConfigFactory
import shop.{ PaymentService, PerformRequest}


/*
  errors handled:
  - what if a server is not set up(connection refused due to an improper port binding, the connection timeout)
  - what if payload is incorrect (expected string parsable to float, got sth not parsable instead)
  - surviving situations of http exceptions thrown by hardcoded endpoints
 */

class SupervisionTestActor(items: List[Item]) extends Actor{

  private def incorrectRequest(errorCode: String) =
    HttpRequest(
      method = HttpMethods.POST,
      uri = s"http://192.168.0.107/error-$errorCode",
      entity = HttpEntity.Empty
    )

  private def malformedNumberRequest() =
    HttpRequest(
      method = HttpMethods.POST,
      uri = "http://192.168.0.107/payment",
      entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, "ala ma kota")
    )

  override  def receive: Receive = {
    case StartPaymentService() =>
      val paymentService = context actorOf Props(new PaymentService(items))
      paymentService ! PerformRequest(incorrectRequest("404"))
      paymentService ! PerformRequest(incorrectRequest("403"))
      paymentService ! PerformRequest(incorrectRequest("401"))
      paymentService ! PerformRequest(malformedNumberRequest())
  }

  override val supervisorStrategy: OneForOneStrategy =
    OneForOneStrategy() {
      case httpExc : HttpException =>
        println("Http exc: " + httpExc.getMessage)
        Restart
      case iae: IllegalArgumentException=>
        println("Illegal Arg Exc: " + iae.getMessage)
        Restart
      case e: Exception =>
        println("Escalating exception: " + e.getMessage)
        Escalate
    }
}

case class StartPaymentService()

object PayPalClientApp{

  def main(args: Array[String]): Unit ={
    val config = ConfigFactory.load()
    val system: ActorSystem = ActorSystem("TestSystem", config.getConfig("clientapp").withFallback(config))
    val item = Item(new URI("a"), "a", 130, 0)
    val refItem = Item(new URI("b"), "b", 25, 1)
    val items = List(item, item, item,  refItem, item, item, refItem)

    val payPalTestClient = system.actorOf(Props(new SupervisionTestActor(items)))
    payPalTestClient ! StartPaymentService()


  }
}
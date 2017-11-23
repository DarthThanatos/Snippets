package shop

import java.net.URI

import akka.actor.{Actor, ActorRef, ActorSelection, Props}
import communication.{Answer, Item, Query}

import scala.util.Random

class Customer extends Actor{

  println("Customer actor created")
  val cart: ActorRef = context actorOf(Props( new CartManager ("0")), "cart")
  val paymentCatalogue: ActorSelection = context.actorSelection("akka.tcp://RemoteSystem@127.0.0.1:2552/user/PaymentCatalogue")

  paymentCatalogue ! Query("Nurturme")

  private def handleAnswer(items: List[Item]): Unit = {
    for {item @ Item(uri, name, price, inStock) <- items; if inStock >= 1} {
        println("Customer processing: " + name)
        val wantedItem = item.copy(count = ( math.abs(new Random().nextInt) % inStock) + 1)
        cart ! AddItem(wantedItem)
    }
    cart ! StartCheckout()
  }

  override def receive: Receive = {
    case Answer(items: List[Item]) =>
      handleAnswer(items)

    case CheckoutStarted(checkout: ActorRef) =>
      checkout ! SelectDelivery("dpd")
      checkout ! SelectPayment("zl")

    case PaymentServiceStarted(paymentService: ActorRef) =>
      paymentService ! DoPayment

    case CheckoutClosed() =>
      println("Customer got CheckoutClosed message")

    case CheckoutCancelled() =>
      println("Customer got CheckoutCancelled message")

    case CartEmpty =>
      println("Cart empty, finishing this pathetic form from the face of the universe")
      context stop self
  }
}

package shop

import java.net.URI

import akka.actor.{Actor, ActorRef, Props}
import communication.{Answer, Item}
import scala.util.Random

class Customer extends Actor{

  println("Customer actor created")
  val cart: ActorRef = context actorOf(Props[CartManager], "cart")
//  val paymentCatalogue

//  cart ! AddItem(Item(new URI("food/1"), "cheese", 213, 6))

  private def handleAnswer(items: List[Item]): Unit = {
    for {item @ Item(uri, name, price, inStock) <- items; if inStock >= 1} {
        val wantedItem = item.copy(count = (new Random().nextInt % inStock) + 1)
        cart ! wantedItem
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

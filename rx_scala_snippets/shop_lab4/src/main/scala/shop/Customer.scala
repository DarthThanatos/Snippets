package shop

import java.net.URI

import akka.actor.{Actor, ActorRef, Props}

class Customer extends Actor{

  println("Customer actor created")
  val cart: ActorRef = context actorOf(Props[CartManager], "cart")
  cart ! AddItem(Item(new URI("food/1"), "cheese", 213, 6))
  cart ! StartCheckout()

  override def receive: Receive = {
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

package shop

import akka.actor.{Actor, ActorRef, Props}

class Customer extends Actor{

  println("Customer actor created")
  val cart: ActorRef = context actorOf(Props[Cart], "cart")
  cart ! ItemAdded()
  cart ! StartCheckout()

  override def receive: Receive = {
    case CheckoutStarted(checkout: ActorRef) =>
      checkout ! DeliverySelected("dpd")
      checkout ! PaymentSelected("zl")

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

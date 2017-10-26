package shop

import akka.actor.ActorRef

case class GetState()

sealed trait CartMessage
case object UnitializedCart extends CartMessage
case class ItemAdded() extends CartMessage
case class ItemRemoved() extends CartMessage
case class CartTimerExpired() extends CartMessage

sealed trait CheckoutMessage
case object UnitializedCheckout extends CheckoutMessage
case class StartCheckout() extends CheckoutMessage
case class CheckoutClosed() extends CheckoutMessage
case class CheckoutCancelled() extends CheckoutMessage
case class CheckoutTimerExpired() extends CheckoutMessage
case class PaymentSelected(paymentMethod: String) extends CheckoutMessage
case class PaymentTimerExpired() extends CheckoutMessage
case class PaymentReceived(payment: String) extends CheckoutMessage
case class DeliverySelected(deliveryMethod: String) extends CheckoutMessage


sealed trait CustomerMessage
case object CartEmpty extends CustomerMessage
case object PaymentConfirmed extends CustomerMessage
case class CheckoutStarted(actorRef : ActorRef) extends CustomerMessage

sealed trait PaymentServiceMessage
case object DoPayment extends PaymentServiceMessage
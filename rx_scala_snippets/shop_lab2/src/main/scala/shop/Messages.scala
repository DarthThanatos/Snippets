package shop

import akka.actor.{Actor, ActorRef}

case class GetState()

sealed trait CartMessage
case object UnitializedCart extends CartMessage
case class ItemAdded() extends CartMessage
case class ItemRemoved() extends CartMessage
case class CartTimerExpired() extends CartMessage

sealed trait CheckoutMessage
case object UnitializedCheckout extends CheckoutMessage
case class CheckoutStarted() extends CheckoutMessage
case class CheckoutClosed() extends CheckoutMessage
case class CheckoutCancelled() extends CheckoutMessage
case class CheckoutTimerExpired() extends CheckoutMessage
case class PaymentSelected(paymentMethod: String) extends CheckoutMessage
case class PaymentTimerExpired() extends CheckoutMessage
case class PaymentReceived(payment: String) extends CheckoutMessage
case class DeliverySelected(deliveryMethod: String) extends CheckoutMessage



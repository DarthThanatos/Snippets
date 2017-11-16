package shop

import akka.actor.ActorRef
import communication.Item

case class GetState()

sealed trait CartMessage
case object UnitializedCart extends CartMessage
case class AddItem(item: Item) extends CartMessage
case class RemoveItem(item: Item) extends CartMessage
case class CartTimerExpired() extends CartMessage

sealed trait CheckoutMessage
case object UnitializedCheckout extends CheckoutMessage
case class StartCheckout() extends CheckoutMessage
case class CheckoutClosed() extends CheckoutMessage
case class CheckoutCancelled() extends CheckoutMessage
case class CheckoutTimerExpired() extends CheckoutMessage
case class SelectPayment(paymentMethod: String) extends CheckoutMessage
case class PaymentTimerExpired() extends CheckoutMessage
case class ReceivePayment(payment: Float) extends CheckoutMessage
case class SelectDelivery(deliveryMethod: String) extends CheckoutMessage


sealed trait CustomerMessage
case object CartEmpty extends CustomerMessage
case object PaymentConfirmed extends CustomerMessage
case class CheckoutStarted(checkout : ActorRef) extends CustomerMessage
case class PaymentServiceStarted(paymentService: ActorRef) extends CustomerMessage

sealed trait PaymentServiceMessage
case object DoPayment extends PaymentServiceMessage
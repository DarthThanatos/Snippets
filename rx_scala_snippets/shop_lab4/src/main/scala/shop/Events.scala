package shop

import akka.util.Timeout

import scala.concurrent.duration._

trait CartEvent
case class ItemAdded(item:Item) extends CartEvent
case class ItemRemoved(item: Item) extends CartEvent
case class CartEmptied() extends CartEvent

trait CheckoutEvent
case class DeliverySelected(delivery: String) extends CheckoutEvent
case class PaymentSelected(payment: String) extends CheckoutEvent
case class PaymentReceived(amount: String) extends CheckoutEvent
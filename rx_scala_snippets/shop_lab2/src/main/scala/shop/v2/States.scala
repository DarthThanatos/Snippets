package shop.v2

sealed trait CartState
case class Empty() extends CartState
case class NonEmpty() extends CartState
case class InCheckout() extends CartState

sealed trait CheckoutState
case class SelectingDelivery() extends CheckoutState
case class Cancelled() extends CheckoutState
case class SelectingPayment() extends CheckoutState
case class ProcessingPayment() extends CheckoutState
case class Closed() extends CheckoutState
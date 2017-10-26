package shop

sealed trait CartState
case object Empty extends CartState
case object NonEmpty extends CartState
case object InCheckout extends CartState

sealed trait CheckoutState
case object SelectingDelivery extends CheckoutState
case object SelectingPaymentMethod extends CheckoutState
case object ProcessingPayment extends CheckoutState

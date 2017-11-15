package shop

import akka.persistence.fsm.PersistentFSM.FSMState

sealed trait CartState extends FSMState
case object Empty extends CartState {
  override def identifier = "Empty"
}
case object NonEmpty extends CartState {
  override def identifier = "NonEmpty"
}
case object InCheckout extends CartState {
  override def identifier = "InCheckout"
}

case object CartStopped extends CartState{
  override def identifier = "CartStopped"
}

sealed trait CheckoutState extends FSMState
case object SelectingDelivery extends CheckoutState {
  override def identifier = "SelectingDelivery"
}
case object SelectingPaymentMethod extends CheckoutState {
  override def identifier = "SelectingPaymentMethod"
}
case object ProcessingPayment extends CheckoutState {
  override def identifier = "ProcessingPayment"
}


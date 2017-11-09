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

sealed trait CheckoutState
case object SelectingDelivery extends CheckoutState
case object SelectingPaymentMethod extends CheckoutState
case object ProcessingPayment extends CheckoutState


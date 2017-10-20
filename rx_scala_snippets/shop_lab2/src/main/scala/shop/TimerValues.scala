package shop


object TimerValues{

  val cartTimer = 5
  case object CartTimerKey
  case object CartTimerExpired

  val checkoutTimer = 30
  case object CheckoutTimerKey
  case object CheckoutTimerExpired

  val paymentTimer = 15
  case object PaymentTimerKey
  case object PaymentTimerExpired

}

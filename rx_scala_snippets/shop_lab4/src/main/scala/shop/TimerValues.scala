package shop

object TimerValues{

  val tickerDelay = 1

  val cartTimer = 8
  case object CartTimerKey
  case object CartTimerExpired

  val checkoutTimer = 2
  case object CheckoutTimerKey
  case object CheckoutTimerExpired

  val paymentTimer = 2

  case object PaymentTimerKey
  case object PaymentTimerExpired

}

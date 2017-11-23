package shop

object TimerValues{

  val tickerDelay = 1

  val cartTimer = 8
  case object CartTimerKey
  case object CartTimerExpired

  val checkoutTimer = 20
  case object CheckoutTimerKey
  case object CheckoutTimerExpired

  val paymentTimer = 20

  case object PaymentTimerKey
  case object PaymentTimerExpired

}

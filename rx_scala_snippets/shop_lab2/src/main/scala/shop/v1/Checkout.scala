package shop
package v1

import akka.actor.{Actor, Timers}
import scala.concurrent.duration._

class Checkout extends Actor with Timers{
  override def receive: Receive = SelectingDelivery
  private val checkoutTimer = 3
  private case object CheckoutTimerKey
  private case object CheckoutTimerTick

  def SelectingDelivery: Receive = {
    case CheckoutStarted() =>
      timers.startSingleTimer(CheckoutTimerKey, CheckoutTimerTick, checkoutTimer seconds)
    case DeliverySelected(deliveryMethod : String ) =>
      println("Chose delivery method: " + deliveryMethod)
    case CheckoutCancelled() =>
    case CheckoutTimerTick =>

  }

  def SelectingPayment: Receive = {
    case PaymentSelected(paymentMethod: String) =>
    case CheckoutCancelled() =>
    case CheckoutTimerTick =>
  }

  def Canelled: Receive = {
    case CheckoutCancelled() =>
    case CheckoutTimerTick =>
  }

}

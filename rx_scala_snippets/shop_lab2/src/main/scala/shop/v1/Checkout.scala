package shop
package v1

import akka.actor.{Actor, ActorRef, Timers}

import scala.concurrent.duration._
import TimerValues.{CheckoutTimerKey, PaymentTimerKey, checkoutTimer, paymentTimer}

class Checkout(val cart: ActorRef) extends Actor with Timers{

  println("Created: " + self.path.toSerializationFormat)
  timers.startSingleTimer(CheckoutTimerKey, CheckoutTimerExpired, checkoutTimer seconds)
  override def receive: Receive = SelectingDelivery

  def SelectingDelivery: Receive = {
    case DeliverySelected(deliveryMethod: String) =>
      println("Chosen delivery method: " + deliveryMethod)
      context become SelectingPaymentMethod
    case CheckoutCancelled() =>
      println("Selecting delivery: Checkout Cancelled")
      Cancelled()
    case CheckoutTimerExpired =>
      println("Checkout expired")
      Cancelled()
    case GetState() => println("Checkout-SelectingDelivery")
  }

  def SelectingPaymentMethod: Receive = {
    case PaymentSelected(paymentMethod: String) =>
      println("Payment method: " + paymentMethod)
      timers.startSingleTimer(PaymentTimerKey, PaymentTimerExpired, paymentTimer seconds)
      context become ProcessingPayment
    case CheckoutCancelled() =>
      println("Selecting payment: Checkout Cancelled")
      Cancelled()
    case CheckoutTimerExpired =>
      println("selecting payment Checkout expired")
      Cancelled()
    case GetState() => println("Checkout-SelectingPaymentMethod")
  }


  def ProcessingPayment: Receive = {
    case PaymentReceived(payment: String) =>
      println("Got payment: " + payment)
      Closed()
    case CheckoutCancelled() =>
      println("Processing payment: Checkout Cancelled")
      Cancelled()
    case PaymentTimerExpired =>
      println("processing payment Checkout expired")
      Cancelled()
    case GetState() => println("Checkout-ProcessingPayment")
  }

  def Closed(): Unit ={
    println("Closing checkout")
    cart ! CheckoutClosed()
    context.stop(self)
  }

  def Cancelled(): Unit ={
    println("Cancelling checkout")
    cart ! CheckoutCancelled()
    context.stop(self)
  }
}


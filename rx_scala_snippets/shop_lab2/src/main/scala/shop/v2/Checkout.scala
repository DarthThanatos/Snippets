package shop
package v2

import akka.actor.{Actor, ActorRef, FSM, Timers}
import shop.TimerValues.{CheckoutTimerKey, PaymentTimerKey, checkoutTimer, paymentTimer}

import scala.concurrent.duration._
class Checkout(val cart: ActorRef) extends Actor with FSM[CheckoutState, CheckoutMessage] with Timers{

  println("Created: " + self.path.toSerializationFormat)
  timers.startSingleTimer(CheckoutTimerKey, CheckoutTimerExpired, checkoutTimer seconds)
  startWith(SelectingDelivery, UnitializedCheckout)

  when (SelectingDelivery){
    case Event(DeliverySelected(deliveryMethod: String), _) =>
      println("Chosen delivery method: " + deliveryMethod)
      goto(SelectingPaymentMethod)
    case Event(CheckoutCancelled(), _) =>
      println("Selecting delivery: Checkout Cancelled")
      Cancelled()
      stay
    case Event(CheckoutTimerExpired(), _) =>
      println("Checkout expired")
      Cancelled()
      stay
    case Event(GetState(), _) =>
      println("Checkout-SelectingDelivery")
      stay
  }

  when(SelectingPaymentMethod){
    case Event(PaymentSelected(paymentMethod: String), _) =>
      println("Payment method: " + paymentMethod)
      timers.startSingleTimer(PaymentTimerKey, PaymentTimerExpired, paymentTimer seconds)
      goto(ProcessingPayment)
    case Event(CheckoutCancelled, _) =>
      println("Selecting payment: Checkout Cancelled")
      Cancelled()
      stay
    case Event(GetState(), _) =>
      println("Checkout-SelectingPaymentMethod")
      stay
  }

  when(ProcessingPayment){
    case Event(PaymentReceived(payment: String), _) =>
      println("Got payment: " + payment)
      Closed()
      stay
    case Event(CheckoutCancelled, _) =>
      println("Processing payment: Checkout Cancelled")
      Cancelled()
      stay
    case Event(PaymentTimerExpired, _) =>
      println("processing payment Checkout expired")
      Cancelled()
      stay
    case Event(GetState(), _) =>
      println("Checkout-ProcessingPayment")
      stay
  }

  whenUnhandled{
    case Event(e, s) =>
      stay
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


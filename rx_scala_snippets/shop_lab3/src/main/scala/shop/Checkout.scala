package shop

import akka.actor.{Actor, ActorRef, FSM, Props}
import shop.TimerValues.{checkoutTimer, paymentTimer}

import scala.concurrent.duration._

class Checkout(val cart: ActorRef) extends Actor with FSM[CheckoutState, CheckoutMessage]{

  startWith(SelectingDelivery, UnitializedCheckout)

  when (SelectingDelivery, stateTimeout = checkoutTimer seconds){

    case Event(ds @ DeliverySelected(deliveryMethod: String), _) =>
      goto(SelectingPaymentMethod) using ds

    case Event(StateTimeout, _) =>
      performReasonedOp("Checkout expired", Cancelled)

    case Event(CheckoutCancelled(), _) =>
      performReasonedOp("Selecting delivery: Checkout Cancelled", Cancelled)


    case Event(GetState(), _) =>
      fetchState("Checkout-SelectingDelivery")

    case ev => println (ev); stay
  }

  private def performReasonedOp(reason: String, op: () => Unit) ={
    println(reason)
    op()
    stay
  }

  private def fetchState(state: String) ={
    println(state); stay
  }

  onTransition{
    case SelectingDelivery -> SelectingPaymentMethod =>
         nextStateData match {
          case DeliverySelected(deliveryMethod: String) => println("Chosen delivery method: " + deliveryMethod)
        }
  }

  when(SelectingPaymentMethod, stateTimeout = checkoutTimer seconds){
    case Event(ps @ PaymentSelected(paymentMethod: String), _) =>
      goto(ProcessingPayment) using ps

    case Event(StateTimeout, _) =>
      performReasonedOp("Payment method selection timeout", Cancelled)

    case Event(CheckoutCancelled(), _) =>
      performReasonedOp("Selecting payment: Checkout Cancelled", Cancelled)

    case Event(GetState(), _) =>
      fetchState("Checkout-SelectingPaymentMethod")
  }

  onTransition{
    case SelectingPaymentMethod -> ProcessingPayment =>
      nextStateData match {
        case PaymentSelected(paymentMethod: String) =>
          println("Payment method: " + paymentMethod)
          val paymentService = context actorOf(Props[PaymentService], "paymentService")
          context.actorSelection("/user/customer") ! PaymentServiceStarted(paymentService)
        case _ =>
      }
  }

  when(ProcessingPayment, stateTimeout = paymentTimer seconds){
    case Event(PaymentReceived(payment: String), _) =>
      performReasonedOp("Got payment: " + payment, Closed)

    case Event(CheckoutCancelled(), _) =>
      performReasonedOp("Processing payment: Checkout Cancelled", Cancelled)

    case Event(StateTimeout, _) =>
      performReasonedOp("processing payment Checkout expired", Cancelled)

    case Event(GetState(), _) =>
      fetchState("Checkout-ProcessingPayment")
  }

  whenUnhandled{
    case Event(e, s) =>
      println ("State: " + s + " event: " + e)
      stay
  }

  private def finishingOp(desc: String, msg : Any): Unit ={
    println(desc)
    cart ! msg
    context.actorSelection("/user/customer") ! msg
    context.stop(self)

  }

  def Closed(): Unit ={
    finishingOp("Closing checkout", CheckoutClosed())
  }

  def Cancelled(): Unit ={
    finishingOp("Cancelling checkout", CheckoutCancelled())
  }

  initialize()
}


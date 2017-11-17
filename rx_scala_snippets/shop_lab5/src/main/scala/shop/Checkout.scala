package shop

import akka.actor.{Actor, ActorRef, Props}
import akka.persistence.fsm.PersistentFSM
import communication.Item
import shop.TimerValues.{checkoutTimer, paymentTimer}

import scala.concurrent.duration._
import scala.reflect.{ClassTag, classTag}

case class CheckoutData(data: List[String])

class Checkout(val cart: ActorRef, val id: String, items: List[Item])(implicit val domainEventClassTag: ClassTag[CheckoutEvent]) extends Actor with PersistentFSM[CheckoutState, CheckoutData, CheckoutEvent]{
  println("Checkout started")
  startWith(SelectingDelivery, CheckoutData(List()))

  when (SelectingDelivery, stateTimeout = checkoutTimer seconds){

    case Event(SelectDelivery(deliveryMethod: String), _) =>
      goto(SelectingPaymentMethod) applying DeliverySelected(deliveryMethod)

    case Event(StateTimeout, _) =>
      performReasonedOp("Checkout expired", Cancelled)

    case Event(CheckoutCancelled(), _) =>
      performReasonedOp("Selecting delivery: Checkout Cancelled", Cancelled)

  }

  private def performReasonedOp(reason: String, op: () => Unit) ={
    println(reason)
    op()
    stay
  }

  override def applyEvent(event: CheckoutEvent, checkoutDataBeforeEvent: CheckoutData): CheckoutData = event match{
    case DeliverySelected(deliveryMethod: String)=>
      checkoutDataBeforeEvent.copy(data = deliveryMethod :: checkoutDataBeforeEvent.data)
    case PaymentSelected(paymentMethod: String) =>
      checkoutDataBeforeEvent.copy(data = paymentMethod :: checkoutDataBeforeEvent.data)
    case PaymentReceived(amount: Float) =>
      checkoutDataBeforeEvent.copy(data = amount.toString :: checkoutDataBeforeEvent.data)
  }

  onTransition{
    case SelectingPaymentMethod -> ProcessingPayment =>
      val paymentService = context actorOf Props(new PaymentService(items))
      context.actorSelection("/user/customer") ! PaymentServiceStarted(paymentService)
  }

  when(SelectingPaymentMethod, stateTimeout = checkoutTimer seconds){

    case Event(ps @ SelectPayment(paymentMethod: String), _) =>
      goto(ProcessingPayment) applying PaymentSelected(paymentMethod)

    case Event(StateTimeout, _) =>
      performReasonedOp("Payment method selection timeout", Cancelled)

    case Event(CheckoutCancelled(), _) =>
      performReasonedOp("Selecting payment: Checkout Cancelled", Cancelled)

  }

  when(ProcessingPayment, stateTimeout = paymentTimer seconds){

    case Event(ReceivePayment(payment: Float), _) =>
      applyEvent(PaymentReceived(payment), stateData)
      performReasonedOp("Got payment: " + payment, Closed)

    case Event(CheckoutCancelled(), _) =>
      performReasonedOp("Processing payment: Checkout Cancelled", Cancelled)

    case Event(StateTimeout, _) =>
      performReasonedOp("processing payment Checkout expired", Cancelled)
  }

  whenUnhandled {
    case Event(GetState(), _) =>
      println(stateName)
      sender ! stateName
      stay
    case Event(e, s) =>
      println ("State: " + s + " event: " + e)
      stay
  }

  private def finishingOp(desc: String, msg : Any): Unit = {
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

//  override implicit def domainEventClassTag: ClassTag[CheckoutEvent] = classTag[CheckoutEvent]

  override def persistenceId: String = "persistent-checkout-fsm-id-" + id

}
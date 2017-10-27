package shop

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}
import scala.concurrent.duration._

class ParentChildSpec extends TestKit(ActorSystem("CartSpec"))
  with WordSpecLike with BeforeAndAfterAll with ImplicitSender  {


  override def afterAll(): Unit = {
    system.terminate
  }

  "A Probe Cart" must {

    "get checkout closed msg" in {
      val probe = TestProbe()
      val checkout = system.actorOf(Props(classOf[Checkout], probe.ref))
      checkout ! DeliverySelected("dpd")
      checkout ! PaymentSelected("zl")
      checkout ! PaymentReceived("19")
      probe.expectMsg(CheckoutClosed())
    }
  }

  "Checkout" must {

    "starts in Selecting Delivery state" in {
      val probe = TestProbe()
      val checkout = system.actorOf(Props(classOf[Checkout], probe.ref))
      checkout ! GetState()
      expectMsg(SelectingDelivery)
    }


    "after getting DeliverySelected in SelectingDelivery msg, change its state to SelectingPayment" in{
      val probe = TestProbe()
      val checkout = system.actorOf(Props(classOf[Checkout], probe.ref))
      checkout ! DeliverySelected("dpd")
      checkout ! GetState()
      expectMsg(SelectingPaymentMethod)
    }

    "after getting PaymentSelected in SelectingPayment, change its state to Processing Payment" in {
      val probe = TestProbe()
      val checkout = system.actorOf(Props(classOf[Checkout], probe.ref))
      checkout ! DeliverySelected("dpd")
      checkout ! PaymentSelected("zl")
      checkout ! GetState()
      expectMsg(ProcessingPayment)
    }

    "cancel at any state after having received Cancelled msg" in {
      val probe = TestProbe()
      var checkout = system.actorOf(Props(classOf[Checkout], probe.ref))
      checkout ! CheckoutCancelled()
      checkout ! GetState()
      expectNoMessage(1 second)
      checkout = system.actorOf(Props(classOf[Checkout], probe.ref))
      checkout ! DeliverySelected("dpd")
      checkout ! CheckoutCancelled()
      checkout ! GetState()
      expectNoMessage(1 second)
      checkout = system.actorOf(Props(classOf[Checkout], probe.ref))
      checkout ! DeliverySelected("dpd")
      checkout ! PaymentSelected("zl")
      checkout ! CheckoutCancelled()
      checkout ! GetState()
      expectNoMessage(1 second)
    }

    "cancel after timeout in selecting delivery state" in {
      val probe = TestProbe()
      val checkout = system.actorOf(Props(classOf[Checkout], probe.ref))
      probe.expectMsg((TimerValues.checkoutTimer + 1) seconds ,CheckoutCancelled())
    }

    "cancel after timeout in selecting payment state" in {
      val probe = TestProbe()
      val checkout = system.actorOf(Props(classOf[Checkout], probe.ref))
      checkout ! DeliverySelected("dpd")
      probe.expectMsg((TimerValues.checkoutTimer + 1) seconds ,CheckoutCancelled())
    }

    "cancel after timeout in processing payment state" in {
      val probe = TestProbe()
      val checkout = system.actorOf(Props(classOf[Checkout], probe.ref))
      checkout ! DeliverySelected("dpd")
      checkout ! PaymentSelected("zl")
      probe.expectMsg((TimerValues.paymentTimer + 1) seconds ,CheckoutCancelled())
    }

    "not change its state to processing payment being in selecting delivery state" in {
      val probe = TestProbe()
      val checkout = system.actorOf(Props(classOf[Checkout], probe.ref))
      checkout ! PaymentSelected("zl")
      checkout ! GetState()
      expectMsg(SelectingDelivery)
    }

    "not change its state back to selecting delivery being in selecting payment state" in {
      val probe = TestProbe()
      val checkout = system.actorOf(Props(classOf[Checkout], probe.ref))
      checkout ! DeliverySelected("dpd")
      checkout ! StartCheckout()
      checkout ! GetState()
      expectMsg(SelectingPaymentMethod)
    }

    "not change its state back to selecting delivery being in processing payment state" in {
      val probe = TestProbe()
      val checkout = system.actorOf(Props(classOf[Checkout], probe.ref))
      checkout ! DeliverySelected("dpd")
      checkout ! PaymentSelected("zl")
      checkout ! StartCheckout()
      checkout ! GetState()
      expectMsg(ProcessingPayment)
    }

    "not change its state back to selecting payment when in processing payment state" in {
      val probe = TestProbe()
      val checkout = system.actorOf(Props(classOf[Checkout], probe.ref))
      checkout ! DeliverySelected("dpd")
      checkout ! PaymentSelected("zl")
      checkout ! DeliverySelected("dpd")
      checkout ! GetState()
      expectMsg(ProcessingPayment)
    }

    "not change its state to closed in selecting delivery state" in {
      val probe = TestProbe()
      val checkout = system.actorOf(Props(classOf[Checkout], probe.ref))
      checkout ! CheckoutClosed()
      checkout ! GetState()
      expectMsg(SelectingDelivery)

    }

    "not change its state to closed in selecting payment method state" in {
      val probe = TestProbe()
      val checkout = system.actorOf(Props(classOf[Checkout], probe.ref))
      checkout ! DeliverySelected("dpd")
      checkout ! CheckoutClosed()
      checkout ! GetState()
      expectMsg(SelectingPaymentMethod)

    }
  }

}

// todo
// test of diagram
// tests of different permutations of DeliverySelected, PaymentReceived, PaymentSelected
// tests of timers
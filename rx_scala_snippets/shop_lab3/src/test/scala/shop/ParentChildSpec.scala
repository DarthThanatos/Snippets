package shop

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}


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

}
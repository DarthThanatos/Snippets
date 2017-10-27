package shop

import akka.actor.ActorSystem
import akka.testkit.{TestActorRef, TestKit}
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}

class CartSynchSpec extends TestKit(ActorSystem("CartSpec"))
  with WordSpecLike with BeforeAndAfterAll  {



  override def afterAll(): Unit = {
    system.terminate
  }

  "A Cart" must {

    "be empty at start" in{
      val cart: TestActorRef[Cart] =  TestActorRef[Cart]
      assert (cart.underlyingActor.itemsCount == 0)
      assert (cart.underlyingActor.stateName == Empty)
    }

    "increase the number of items inside it by one and become non empty" in {
      val cart: TestActorRef[Cart] =  TestActorRef[Cart]
      cart ! ItemAdded()
      assert (cart.underlyingActor.itemsCount == 1)
      assert (cart.underlyingActor.stateName == NonEmpty)
    }


    "increase the number of items inside it by one and become non empty, decrease, become empty again" in {
      val cart: TestActorRef[Cart] =  TestActorRef[Cart]
      cart ! ItemAdded()
      assert (cart.underlyingActor.itemsCount == 1)
      assert (cart.underlyingActor.stateName == NonEmpty)
      cart ! ItemRemoved()
      assert (cart.underlyingActor.itemsCount == 0)
      assert (cart.underlyingActor.stateName == Empty)
    }

    "increase the number of items inside it by one, become non empty, increase again, and stay non empty" in {
      val cart: TestActorRef[Cart] =  TestActorRef[Cart]
      cart ! ItemAdded()
      assert (cart.underlyingActor.itemsCount == 1)
      assert (cart.underlyingActor.stateName == NonEmpty)
      cart ! ItemAdded()
      assert (cart.underlyingActor.itemsCount == 2)
      assert (cart.underlyingActor.stateName == NonEmpty)

    }

    "increase by two, become nonempty, decrease by one, stay nonempty" in {
      val cart: TestActorRef[Cart] =  TestActorRef[Cart]
      cart ! ItemAdded()
      cart ! ItemAdded()
      assert (cart.underlyingActor.itemsCount == 2)
      assert (cart.underlyingActor.stateName == NonEmpty)
      cart ! ItemRemoved()
      assert (cart.underlyingActor.itemsCount == 1)
      assert (cart.underlyingActor.stateName == NonEmpty)

    }

    "increase by two, become nonempty, decrease by one, stay nonempty, decrease by one, become empty again" in {
      val cart: TestActorRef[Cart] =  TestActorRef[Cart]
      cart ! ItemAdded()
      cart ! ItemAdded()
      assert (cart.underlyingActor.itemsCount == 2)
      assert (cart.underlyingActor.stateName == NonEmpty)
      cart ! ItemRemoved()
      assert (cart.underlyingActor.itemsCount == 1)
      assert (cart.underlyingActor.stateName == NonEmpty)
      cart ! ItemRemoved()
      assert (cart.underlyingActor.itemsCount == 0)
      assert (cart.underlyingActor.stateName == Empty)

    }

    "in the nonempty state, become empty after the carttimer expired" in {
      val cart: TestActorRef[Cart] =  TestActorRef[Cart]
      cart ! ItemAdded()
      Thread.sleep( (TimerValues.cartTimer + 1) * 1000)
      assert(cart.underlyingActor.stateName == Empty)
      assert(cart.underlyingActor.itemsCount == 0)
    }

    "in the nonempty state, refresh timer just before  the carttimer expiring, and stay nonempty after an immediate check" in {
      val cart: TestActorRef[Cart] =  TestActorRef[Cart]
      cart ! ItemAdded()
      Thread.sleep( (TimerValues.cartTimer - 1) * 1000)
      cart ! ItemAdded()
      assert(cart.underlyingActor.stateName == NonEmpty)
      assert(cart.underlyingActor.itemsCount == 2)

    }

    "in the empty state, ignore startcheckout msg" in {
      val cart: TestActorRef[Cart] =  TestActorRef[Cart]
      cart ! StartCheckout()
      assert(cart.underlyingActor.itemsCount == 0)
      assert (cart.underlyingActor.stateName == Empty)
    }

    "in the checkout state, after checkouttimer expired, become nonempty (checkout cancelled) with the same amount of items as before checkout" in {
      val cart: TestActorRef[Cart] =  TestActorRef[Cart]
      cart ! ItemAdded()
      assert(cart.underlyingActor.itemsCount == 1)
      assert (cart.underlyingActor.stateName == NonEmpty)
      cart ! StartCheckout()
      assert(cart.underlyingActor.itemsCount == 1)
      assert (cart.underlyingActor.stateName == InCheckout)
      Thread.sleep((TimerValues.checkoutTimer + 1) * 1000)
      assert(cart.underlyingActor.itemsCount == 1)
      assert (cart.underlyingActor.stateName == NonEmpty)
    }

    "in the checkout state, after checkouttimer + carttimer expired, become empty" in {
      val cart: TestActorRef[Cart] =  TestActorRef[Cart]
      cart ! ItemAdded()
      cart ! StartCheckout()
      Thread.sleep((TimerValues.checkoutTimer + TimerValues.cartTimer + 1) * 1000)
      assert(cart.underlyingActor.itemsCount == 0)
      assert (cart.underlyingActor.stateName == Empty)
    }
  }

  "in the nonempty state, become InCheckout after appropriate msg " in {
    val cart: TestActorRef[Cart] =  TestActorRef[Cart]
    cart ! ItemAdded()
    cart ! StartCheckout()
    assert(cart.underlyingActor.itemsCount == 1)
    assert (cart.underlyingActor.stateName == InCheckout)
  }

}


// todo:
// tests of various add/del messages, content of tests the same as in a CartAsynchSpec
// tests of states implied by above msgs streams, -||-
// tests of timers, -||-
package shop

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}

import scala.concurrent.duration._

class CartAsynchSpec extends TestKit(ActorSystem("CartSpec"))
  with WordSpecLike with BeforeAndAfterAll with ImplicitSender  {


  override def afterAll(): Unit = {
    system.terminate
  }

  "A Cart" must {

    def expect( cart: ActorRef, items: Int, cartState: CartState){
      cart ! "items"
      expectMsg(items)
      cart ! GetState()
      expectMsg(cartState)
    }

    "be empty at start" in{
      val cart = system.actorOf(Props[Cart])
      expect(cart, 0, Empty)
    }

    "still be empty after the ItemRemoved message in Empty state"{
      val cart = system.actorOf(Props[Cart])
      cart ! ItemRemoved()
      expect(cart, 0, Empty)

    }

    "increase the number of items inside it by one and become non empty" in {
      val cart = system.actorOf(Props[Cart])
      cart ! ItemAdded()
      expect(cart, 1, NonEmpty)
    }

    "increase the number of items inside it by one and become non empty, decrease, become empty again" in {
      val cart = system.actorOf(Props[Cart])
      cart ! ItemAdded()
      cart ! ItemRemoved()
      expect(cart, 0, Empty)
    }

    "increase the number of items inside it by one, become non empty, increase again, and stay non empty" in {
      val cart = system.actorOf(Props[Cart])
      cart ! ItemAdded()
      expect(cart, 1, NonEmpty)
      cart ! ItemAdded()
      expect(cart, 2, NonEmpty)

    }

    "increase by two, become nonempty, decrease by one, stay nonempty" in {
      val cart = system.actorOf(Props[Cart])
      cart ! ItemAdded()
      cart ! ItemAdded()
      expect(cart, 2, NonEmpty)
      cart ! ItemRemoved()
      expect(cart, 1, NonEmpty)
    }

    "increase by two, become nonempty, decrease by one, stay nonempty, decrease by one, become empty again" in {
      val cart = system.actorOf(Props[Cart])
      cart ! ItemAdded()
      cart ! ItemAdded()
      expect(cart, 2, NonEmpty)
      cart ! ItemRemoved()
      expect(cart, 1, NonEmpty)
      cart ! ItemRemoved()
      expect(cart, 0, Empty)
    }

    "in the nonempty state, become empty after the carttimer expired" in {
        val cart = system.actorOf(Props[Cart])
        cart ! ItemAdded()
        expectNoMessage((TimerValues.cartTimer+ 1) seconds)
        expect(cart, 0, Empty)
    }

    "in the nonempty state, refresh timer just before  the carttimer expiring, and stay nonempty after an immediate check" in {
        val cart = system.actorOf(Props[Cart])
        cart ! ItemAdded()
        expectNoMessage((TimerValues.cartTimer - 1) seconds)
        cart ! ItemAdded()
        expect(cart, 2, NonEmpty)

    }

    "in the empty state, ignore startcheckout msg" in {
        val cart = system.actorOf(Props[Cart])
        cart ! StartCheckout()
        expect(cart, 0, Empty)
    }

    "in the checkout state, after checkouttimer expired, become nonempty (checkout cancelled) with the same amount of items as before checkout" in {
        val cart = system.actorOf(Props[Cart])
        cart ! ItemAdded()
        expect(cart, 1, NonEmpty)
        cart ! StartCheckout()
        expect(cart, 1, InCheckout)
        expectNoMessage((TimerValues.checkoutTimer + 1) seconds)
        expect(cart, 1, NonEmpty)
    }

    "in the checkout state, after checkouttimer + carttimer expired, become empty" in {
        val cart = system.actorOf(Props[Cart])
        cart ! ItemAdded()
        cart ! StartCheckout()
        expectNoMessage((TimerValues.checkoutTimer + TimerValues.cartTimer + 1) seconds)
        expect(cart, 0, Empty)
    }

    "in the nonempty state, become InCheckout after appropriate msg " in {
      val cart = system.actorOf(Props[Cart])
      cart ! ItemAdded()
      cart ! StartCheckout()
      expect(cart, 1, InCheckout)
    }
  }



}

// todo:
// tests of various add/del messages 5
// tests of states implied by above msgs streams 5
// tests of timers 5
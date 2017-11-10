package shop

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}

import scala.concurrent.duration._
import java.net.URI
import java.util.concurrent.TimeUnit

import com.typesafe.config.ConfigFactory

import scala.concurrent.Await

class CartManagerAsynchSpec extends TestKit(ActorSystem("CartSpec",ConfigFactory.load().getConfig("firstSystem")))
  with WordSpecLike with BeforeAndAfterAll with ImplicitSender  {


  override def afterAll(): Unit = {
    system.terminate
  }

  "A Cart" must {

    "be empty at start" in{
      val cart = system.actorOf(Props(new CartManager("0")))
      expect(cart, 0, Empty)
      system.stop(cart)
    }

    "increase the number of items inside it by one and become non empty" in {
      val cart = system.actorOf(Props(new CartManager("1")))
      cart ! AddItem(Item(new URI("food/1"), "cheese", 213, 3))
      expect(cart, 1, NonEmpty)
      system.stop(cart)
    }

    def probeItemsOfType(cart:ActorRef, typeUri: URI, expected: Int): Unit = {
      cart ! ("countOfType", typeUri)
      expectMsg(expected)
    }

    def expect( cart: ActorRef, itemsTypesCount: Int, cartState: CartState){
      cart ! "items"
      expectMsg( itemsTypesCount )
      cart ! GetState()
      expectMsg(cartState)
    }

    def expectInProbe(cart: ActorRef, itemsTypesCount: Int, cartState: CartState): Unit ={
      val probe = TestProbe()
      cart.tell("items", probe.ref)
      probe.expectMsg( itemsTypesCount )
      cart.tell(GetState(), probe.ref)
      probe.expectMsg(cartState)
    }

    "increase the number of items inside it and become non empty, decrease, become empty again" in {
      val cart = system.actorOf(Props(new CartManager("2")))
      cart ! AddItem(Item(new URI("food/1"), "cheese", 213, 3))
      cart ! RemoveItem(Item(new URI("food/1"), "cheese", 213, 3))
      expect(cart, 0, Empty)
      system.stop(cart)
    }

    "increase the number of items inside it by one, become non empty with one type of an item, increase again, and stay non empty with one type" in {
      val cart = system.actorOf(Props(new CartManager("3")))
      cart ! AddItem(Item(new URI("food/1"), "cheese", 213, 3))
      expect(cart, 1, NonEmpty)
      cart ! AddItem(Item(new URI("food/1"), "cheese", 213, 3))
      expect(cart, 1, NonEmpty)
      system.stop(cart)

    }

    "increase by two, become nonempty, decrease by one, stay nonempty" in {
      val cart = system.actorOf(Props(new CartManager("4")))
      cart ! AddItem(Item(new URI("food/1"), "cheese", 213, 3))
      cart ! AddItem(Item(new URI("food/1"), "cheese", 213, 3))
      expect(cart, 1, NonEmpty)
      cart ! RemoveItem(Item(new URI("food/1"), "cheese", 213, 3))
      expect(cart, 1, NonEmpty)
      system.stop(cart)
    }

    "increase items of type by 6, decrease by 2 and end up with 4 items of type" in {
      val cart = system.actorOf(Props(new CartManager("5")))
      cart ! AddItem(Item(new URI("food/1"), "cheese", 213, 6))
      probeItemsOfType(cart, new URI("food/1"), 6)
      cart ! RemoveItem(Item(new URI("food/1"), "cheese", 213, 2))
      expect(cart, 1, NonEmpty)
      probeItemsOfType(cart, new URI("food/1"), 4)
      system.stop(cart)
    }


    "increase items of type by 6, decrease by 6 and become empty" in {
      val cart = system.actorOf(Props(new CartManager("6")))
      cart ! AddItem(Item(new URI("food/1"), "cheese", 213, 6))
      cart ! RemoveItem(Item(new URI("food/1"), "cheese", 213, 6))
      expect(cart, 0, Empty)
      probeItemsOfType(cart, new URI("food/1"), -1)
      system.stop(cart)
    }

    "in the empty state, ignore startcheckout msg" in {
      val cart = system.actorOf(Props(new CartManager("7")))
        cart ! StartCheckout()
        expect(cart, 0, Empty)
      system.stop(cart)
    }

    "in the nonempty state terminate with the system, and then, after recreating, have an appropriate timeout set" in {
//      system.terminate()
//      Await.ready(system.whenTerminated, Duration(1, TimeUnit.MINUTES))
      var timerSystem = ActorSystem("TimerSystem",ConfigFactory.load().getConfig("secondSystem"))
      var cart = timerSystem.actorOf(Props(new CartManager("7")))
      cart ! AddItem(Item(new URI("food/1"), "cheese", 213, 6))
      expectInProbe(cart, 1, NonEmpty)
      expectNoMessage((TimerValues.cartTimer / 2) .seconds)
      timerSystem.terminate()
      Await.ready(timerSystem.whenTerminated, Duration(1, TimeUnit.MINUTES))
      timerSystem = ActorSystem("TimerSystem",ConfigFactory.load().getConfig("secondSystem"))
      cart = timerSystem.actorOf(Props(new CartManager("7")))
//      expectInProbe(cart, 1, NonEmpty)
      expectNoMessage( ( TimerValues.cartTimer/2 + 3).seconds)
      expectInProbe(cart,0,Empty)
    }
  }

}


// todo:
// tests of various add/del messages 5
// tests of states implied by above msgs streams 5
// tests of timers 5
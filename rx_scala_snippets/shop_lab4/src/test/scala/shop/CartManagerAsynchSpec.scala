package shop

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}

import java.net.URI

class CartManagerAsynchSpec extends TestKit(ActorSystem("CartSpec"))
  with WordSpecLike with BeforeAndAfterAll with ImplicitSender  {


  override def afterAll(): Unit = {
    system.terminate
  }

  "A Cart" must {

    "be empty at start" in{
      val cart = system.actorOf(Props(new CartManager("0")))
      expect(cart, 0, Empty)
    }

    "increase the number of items inside it by one and become non empty" in {
      val cart = system.actorOf(Props(new CartManager("1")))
      cart ! AddItem(Item(new URI("food/1"), "cheese", 213, 3))
      expect(cart, 1, NonEmpty)
    }

    def probeItemsOfType(cart:ActorRef, typeUri: URI, expected: Int): Unit = {
      cart ! ("countOfType", typeUri)
      expectMsg(expected)
    }

    def expect( cart: ActorRef, itemsTypesCount: Int, cartState: CartState){
      cart ! "items"
      expectMsg(itemsTypesCount)
      cart ! GetState()
      expectMsg(cartState)
    }

    "increase the number of items inside it and become non empty, decrease, become empty again" in {
      val cart = system.actorOf(Props(new CartManager("2")))
      cart ! AddItem(Item(new URI("food/1"), "cheese", 213, 3))
      cart ! RemoveItem(Item(new URI("food/1"), "cheese", 213, 3))
      expect(cart, 0, Empty)
    }

    "increase the number of items inside it by one, become non empty with one type of an item, increase again, and stay non empty with one type" in {
      val cart = system.actorOf(Props(new CartManager("3")))
      cart ! AddItem(Item(new URI("food/1"), "cheese", 213, 3))
      expect(cart, 1, NonEmpty)
      cart ! AddItem(Item(new URI("food/1"), "cheese", 213, 3))
      expect(cart, 1, NonEmpty)

    }

    "increase by two, become nonempty, decrease by one, stay nonempty" in {
      val cart = system.actorOf(Props(new CartManager("4")))
      cart ! AddItem(Item(new URI("food/1"), "cheese", 213, 3))
      cart ! AddItem(Item(new URI("food/1"), "cheese", 213, 3))
      expect(cart, 1, NonEmpty)
      cart ! RemoveItem(Item(new URI("food/1"), "cheese", 213, 3))
      expect(cart, 1, NonEmpty)
    }

    "increase items of type by 6, decrease by 2 and end up with 4 items of type" in {
      val cart = system.actorOf(Props(new CartManager("5")))
      cart ! AddItem(Item(new URI("food/1"), "cheese", 213, 6))
      probeItemsOfType(cart, new URI("food/1"), 6)
      cart ! RemoveItem(Item(new URI("food/1"), "cheese", 213, 2))
//      expect(cart, 1, NonEmpty)
      probeItemsOfType(cart, new URI("food/1"), 4)
    }


    "increase items of type by 6, decrease by 6 and become empty" in {
      val cart = system.actorOf(Props(new CartManager("6")))
      cart ! AddItem(Item(new URI("food/1"), "cheese", 213, 6))
      cart ! RemoveItem(Item(new URI("food/1"), "cheese", 213, 6))
      expect(cart, 0, Empty)
      probeItemsOfType(cart, new URI("food/1"), -1)

    }

    "in the empty state, ignore startcheckout msg" in {
      val cart = system.actorOf(Props(new CartManager("7")))
        cart ! StartCheckout()
        expect(cart, 0, Empty)
    }
  }



}

// todo:
// tests of various add/del messages 5
// tests of states implied by above msgs streams 5
// tests of timers 5
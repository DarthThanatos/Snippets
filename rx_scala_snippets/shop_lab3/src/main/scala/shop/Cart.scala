package shop

import akka.actor.{Actor, FSM, Props}
import shop.TimerValues.cartTimer

import scala.language.postfixOps
import scala.concurrent.duration._
import Main.system

class Cart extends Actor with FSM[CartState, CartMessage]{

  println("v2 Created: " + self.path.name)
  var itemsCount = 0
  startWith(Empty, UnitializedCart)

  when(Empty){
    case Event(ItemAdded(), UnitializedCart) =>
      addItemGoingTo(NonEmpty, "v2: An item was added to a cart in the empty state, items count " + itemsCount)

    case Event(GetState(), UnitializedCart) =>
      sender ! "Cart-Empty"
      fetchState("Cart-Empty")
  }

  when(NonEmpty, stateTimeout = cartTimer seconds){
    case Event(ItemAdded(), _) =>
      addItemGoingTo(NonEmpty,"v2: An item was added to a cart in the non-empty state, items count " + itemsCount)

    case Event(ItemRemoved(), _) if itemsCount > 1 =>
      removeItemGoingTo(NonEmpty,"v2: An item was removed from a cart in the non-empty state, still in the non-empty state, items count " + itemsCount)

    case Event(ItemRemoved(), _) =>
      emptyCart("v2: An item was removed from a cart in the non-empty state, now in the empty state, items count " + itemsCount)

    case Event(StartCheckout(), _) =>
      goto(InCheckout)

    case Event(StateTimeout, _) =>
      emptyCart("v2: Cart Timer expired")

    case Event(GetState(), _) =>
      sender ! stateName
      fetchState("Cart-NonEmpty")
  }

  onTransition{
    case NonEmpty -> NonEmpty =>
      println("refreshed timer")

    case NonEmpty -> InCheckout =>
      println("Creating checkout")
      val checkout = system.actorOf(Props(new Checkout(self)), "checkout")
      context.parent ! CheckoutStarted(checkout)
  }

  private def fetchState(state: String) ={
    println(state); stay
  }


  when(InCheckout){
    case Event(CheckoutClosed(), _) =>
      context.parent ! CartEmpty
      emptyCart("v2: InCheckout: Checkout closed")

    case Event(CheckoutCancelled(), _) =>
      goto(NonEmpty)

    case Event(GetState(), _) =>
      fetchState("v2: Cart-InCheckout")
  }

  onTransition{
    case InCheckout -> NonEmpty =>
      println("v2: InCheckout: Checkout cancelled")
  }

  whenUnhandled{
    case Event(e, s) =>
      stay
  }

  private def addItem( msg: => String = ""): Unit ={
    itemsCount += 1
    if(msg != "") println(msg)
  }

  private def addItemGoingTo(state: CartState, msg: => String = "") ={
    addItem(msg)
    goto(state)

  }

  private def removeItem(msg: => String = ""): Unit ={
    itemsCount -= 1
    if(msg != "") println(msg)
  }

  private def removeItemGoingTo(state: CartState, msg : => String = "") ={
    removeItem(msg)
    goto(state)
  }

  private def emptyCart(msg: => String) ={
    itemsCount = 0
    println(msg)
    goto(Empty)
  }

  initialize()
}

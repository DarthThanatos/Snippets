package shop
package v2

import akka.actor.{Actor, FSM, Props, Timers}
import shop.TimerValues.{CartTimerKey, cartTimer}
import shop.v2.Main.system

import scala.concurrent.duration._
import scala.language.postfixOps

class Cart extends Actor with FSM[CartState, CartMessage] with Timers{

  println("v2 Created: " + self.path.name)
  private var itemsCount = 0
  startWith(Empty, UnitializedCart)

  when(Empty){
    case Event(ItemAdded(), UnitializedCart) =>
      addItem()
      timers.startSingleTimer(CartTimerKey, CartTimerExpired, cartTimer seconds)
      println("v2: An item was added to a cart in the empty state, items count " + itemsCount)
      goto(NonEmpty)
    case Event(GetState(), UnitializedCart) =>
      println("v2: Cart-Empty")
      stay
  }

  when(NonEmpty){
    case Event(ItemAdded(), _) =>
      addItem()
      refreshTimer()
      println("v2: An item was added to a cart in the non-empty state, items count " + itemsCount)
      stay
    case Event(ItemRemoved(), _) if itemsCount > 1 =>
      removeItem()
      emptyCart("v2: An item was removed from a cart in the non-empty state, now in the empty state, items count " + itemsCount)
      goto(Empty)

    case Event(ItemRemoved(), _) =>
      removeItem()
      refreshTimer()
      println("v2: An item was removed from a cart in the non-empty state, still in the non-empty state, items count " + itemsCount)
      stay

    case Event(CheckoutStarted(), _) =>
      println("Creating checkout")
      system.actorOf(Props(new Checkout(self)), "checkout")
      goto(InCheckout)

    case Event(CartTimerExpired(), _) =>
      emptyCart("v2: Cart Timer expired")
      goto(Empty)

    case Event(GetState(), _) =>
      emptyCart("v2: [Cart-NonEmpty]")
      goto(Empty)
  }


  when(InCheckout){
    case Event(CheckoutClosed(), _) =>
      emptyCart("v2: InCheckout: Checkout closed")
      goto(Empty)
    case Event(CheckoutCancelled(), _) =>
      emptyCart("v2: InCheckout: Checkout cancelled")
      goto(Empty)
    case Event(GetState(), _) =>
      println("v2: Cart-InCheckout")
      stay
  }

  whenUnhandled{
    case Event(e, s) =>
      stay
  }

  private def refreshTimer(): Unit ={
    println("v2: refreshing cart timer, trigger for " + cartTimer + " seconds from now")
    timers.cancel(CartTimerKey)
    timers.startSingleTimer(CartTimerKey, CartTimerExpired, cartTimer seconds)
  }

  private def addItem(): Unit ={
    itemsCount += 1
  }

  private def removeItem(): Unit ={
    itemsCount -= 1
  }

  private def emptyCart(msg: String): Unit ={
    itemsCount = 0
    println(msg)
  }
}

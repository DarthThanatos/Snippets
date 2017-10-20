package shop
package v1

import akka.actor.{Actor, Props, Timers}

import scala.concurrent.duration._
import TimerValues.{CartTimerKey, CheckoutTimerKey, cartTimer, checkoutTimer}
import shop.v1.Main.system

import scala.language.postfixOps

class Cart extends Actor with Timers{

  println("Created: " + self.path.name)
  private var itemsCount = 0
  override def receive: Receive = Empty

  def Empty : Receive = {
    case ItemAdded() =>
      addItem()
      timers.startSingleTimer(CartTimerKey, CartTimerExpired, cartTimer seconds)
      println("An item was added to a cart in the empty state, items count " + itemsCount)
      context become NonEmpty
    case GetState() => println("Cart-Empty")
//    case wtf => println("[Cart-Empty] Not a valid message: " + wtf)

  }

  def NonEmpty: Receive = {
    case ItemAdded() =>
      addItem()
      refreshTimer()
      println("An item was added to a cart in the non-empty state, items count " + itemsCount)
    case ItemRemoved() if itemsCount > 1 =>
      removeItem()
      refreshTimer()
      println("An item was removed from a cart in the non-empty state, still in the non-empty state, items count " + itemsCount)
    case ItemRemoved() =>
      removeItem()
      emptyCart("An item was removed from a cart in the non-empty state, now in the empty state, items count " + itemsCount)
    case CheckoutStarted() =>
      system.actorOf(Props(new Checkout(self)), "checkout")
      context become InCheckout
    case CartTimerExpired =>
      emptyCart("Cart Timer expired")
    case GetState() => println("Cart-NonEmpty")
//    case wtf => println("[Cart-NonEmpty] Not a valid message: " + wtf)
  }

  def InCheckout: Receive = {
    case CheckoutClosed() =>
      emptyCart("InCheckout: Checkout closed")
    case CheckoutCancelled() =>
      emptyCart("InCheckout: Checkout cancelled")
    case GetState() => println("Cart-InCheckout")
//    case wtf => println("[Cart-InCheckout] Not a valid message: " + wtf)

  }

  private def refreshTimer(): Unit ={
    println("refreshing cart timer, trigger for " + cartTimer + " seconds from now")
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
    context become Empty
  }
}

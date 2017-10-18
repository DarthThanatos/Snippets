package shop
package v1

import akka.actor.{Actor, Props, Timers}

import scala.concurrent.duration._

class Cart extends Actor with Timers{
  private var itemsCount = 0
  private val cartTimer = 5

  private case object CartTimerKey
  private case object CartTimerTick
  override def receive: Receive = Empty

  def Empty : Receive = {
    case ItemAdded() =>
      println("An item was added to a cart in the empty state, items count " + itemsCount)
      itemsCount += 1
      timers.startSingleTimer(CartTimerKey, CartTimerTick, cartTimer seconds)
      context become NonEmpty

  }

  private def refreshTimer(): Unit ={
    timers.cancel(CartTimerKey)
    timers.startSingleTimer(CartTimerKey, CartTimerTick, cartTimer seconds)
  }

  def NonEmpty: Receive = {
    case ItemAdded() =>
      itemsCount += 1
      refreshTimer()
      println("An item was added to a cart in the non-empty state, items count " + itemsCount)
    case ItemRemoved() if itemsCount > 1 =>
      itemsCount -= 1
      refreshTimer()
      println("An item was removed from a cart in the non-empty state, still in the non-empty state, items count " + itemsCount)
    case ItemRemoved() =>
      itemsCount = 0
      timers.cancel(CartTimerKey)
      println("An item was removed from a cart in the non-empty state, now in the empty state, items count " + itemsCount)
      context become Empty
    case cs @ CheckoutStarted() =>
      timers.cancel(CartTimerKey)
      val checkout = context.actorOf(Props[Checkout], "checkout")
      checkout ! cs
      context become InCheckout
    case CartTimerTick =>
      itemsCount = 0
      context become Empty

  }

  def InCheckout: Receive = {
    case CheckoutClosed() =>
    case CheckoutCancelled() =>
  }
}

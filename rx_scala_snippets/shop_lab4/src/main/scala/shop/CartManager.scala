package shop

import java.net.URI

import akka.actor.{Actor, Props, Timers}
import shop.TimerValues.{CartHeartBeatKey, CartHeartBeatTime, CartTimerKey}

import scala.language.postfixOps
import scala.concurrent.duration._
import Main.system
import akka.persistence.fsm.PersistentFSM

import scala.reflect.{ClassTag, classTag}

/**
  * @param id: unique item identifier (java.net.URI)
  */
case class Item(id: URI, name: String, price: BigDecimal, count: Int)

case class Cart(items: Map[URI, Item]) {
  def addItem(it: Item): Cart = {
    val currentCount = if (items contains it.id) items(it.id).count else 0
    println("adding " + it.count + " to current count: " + currentCount)
    copy(items = items.updated(it.id, it.copy(count = currentCount + it.count)))
  }

  def removeItem(it: Item): Cart = {
    //contract - currentCount == -1 => no item of this type in a cart
    val currentCount = if (items contains it.id) items(it.id).count else -1
    println("removing " + it.count + " from current count: " + currentCount)
    if(currentCount == -1)
      this
    else
      if(it.count >= currentCount)
        copy(items = items - it.id)
      else
        copy(items = items.updated(it.id, it.copy(count = currentCount - it.count)))
  }

  def itemsOfTypeCount(typeUri: URI): Int = if (items.contains(typeUri)) items(typeUri).count else -1

  def itemsTypesAmount : Int = items.keys.size

  def itemsAboutToBeEmpty(item: Item) : Boolean =
    itemsTypesAmount == 1 && items.contains(item.id) && (items.get(item.id) match {
      case Some(Item(_,_,_,count)) => count <= item.count
      case None => false
    })
}

object Cart {
  val empty = Cart(Map.empty)
}

class CartManager(id: String) extends Actor with PersistentFSM[CartState, Cart, CartEvent] with Timers{

  override def persistenceId: String = "persistent-cart-fsm-id-" + id
  override def domainEventClassTag: ClassTag[CartEvent] = classTag[CartEvent]

  private var nonEmptyTimeout = TimerValues.cartTimer

  override def applyEvent(event: CartEvent, cartBeforeEvent: Cart): Cart = event match{
    case ctc @ CartTimeoutChanged(persistedTimePassed: Some[FiniteDuration]) =>
      println("applying " +  ctc)
      timers.startSingleTimer(CartTimerKey, CartTimerExpired, persistedTimePassed.get)
      cartBeforeEvent
    case ItemRemoved(item) =>
      cartBeforeEvent.removeItem(item)
    case ItemAdded(item) =>
      println("applying adding")
      cartBeforeEvent.addItem(item)
    case CartEmptied() => Cart.empty
  }

  startWith(Empty, Cart.empty)
  println("Actor CartManager started")

  when(Empty){
    case Event(AddItem(item), cart) =>
      addItemGoingTo(item, NonEmpty, "v2: An item was added to a cart in the empty state, items count " + (cart.items.get(item.id) match {case Some(Item(_, _, _, count)) => count; case None => -1}))
  }


  when(NonEmpty){

    case Event(AddItem(item), cart) =>
      addItemGoingTo(item, NonEmpty,"v2: An item was added to a cart in the non-empty state, items count " + (cart.items.get(item.id) match {case Some(Item(_, _, _, count)) => count; case None => -1}))

    case Event(RemoveItem(item), cart) if !cart.itemsAboutToBeEmpty(item) =>
      removeItemGoingTo(item, NonEmpty, "v2: An item was removed from a cart in the non-empty state, still in the non-empty state, items count " + (cart.items.get(item.id) match {case Some(Item(_, _, _, count)) => count; case None => -1}))

    case Event(RemoveItem(item), cart) =>
      emptyCart("v2: An item was removed from a cart in the non-empty state, now in the empty state, items count " + (cart.items.get(item.id) match {case Some(Item(_, _, _, count)) => count; case None => -1}))

    case Event(StartCheckout(), cart) =>
      goto(InCheckout)

    case Event(CartHeartBeatTime, _) =>
      val ctc = CartTimeoutChanged(Some(FiniteDuration(deadline.timeLeft.toSeconds,SECONDS)))
      stay applying ctc

    case Event(StateTimeout, _) =>
      emptyCart("v2: Cart Timer expired")

    case Event(CartTimerExpired, _ ) =>
      emptyCart("v2: Cart Timer expired")

  }

  var deadline : Deadline = _

  onTransition{
    case Empty -> NonEmpty =>
        deadline = TimerValues.cartTimer.seconds.fromNow
        timers.startPeriodicTimer(CartHeartBeatKey, CartHeartBeatTime, 1 seconds)
    case NonEmpty -> NonEmpty =>
        deadline = TimerValues.cartTimer.seconds.fromNow
        timers.startPeriodicTimer(CartHeartBeatKey, CartHeartBeatTime, 1 seconds)
    case NonEmpty -> InCheckout =>
      val checkout = system.actorOf(Props(new Checkout(self, id)), "checkout")
      context.parent ! CheckoutStarted(checkout)
  }

  private def fetchState(state: String) ={
    println(state); stay
  }


  when(InCheckout){
    case Event(CheckoutClosed(), cart) =>
      context.parent ! CartEmpty
      emptyCart("v2: InCheckout: Checkout closed")

    case Event(CheckoutCancelled(), cart) =>
      goto(NonEmpty)
  }

  onTransition{
    case InCheckout -> NonEmpty =>
      println("v2: InCheckout: Checkout cancelled")

    case NonEmpty -> CartStopped =>
      println("Transition from NonEmpty to CartStopped")
  }

  whenUnhandled{
    case Event(GetState(), cart) =>
      println(stateName)
      sender ! stateName
      stay
    case Event("items",cart) =>
      sender ! cart.itemsTypesAmount
      stay
    case Event(("countOfType", typeUri : URI),cart) =>
      sender ! cart.itemsOfTypeCount(typeUri)
      stay
    case Event(e, s) =>
      stay
  }



  override def aroundPostStop(): Unit = {
    println("Actor CartManager terminated")
    super.postStop()
  }

  private def addItemGoingTo(item: Item, state: CartState,  msg: => String = "") ={
    if(msg != "") println(msg)
    goto(state) applying ItemAdded(item)

  }

  private def removeItemGoingTo(item: Item, state: CartState, msg : => String = "") = {
    if(msg != "") println(msg)
    goto(state) applying ItemRemoved(item)
  }

  private def emptyCart(msg: => String) ={
    println(msg)
    goto(Empty) applying CartEmptied()
  }

}

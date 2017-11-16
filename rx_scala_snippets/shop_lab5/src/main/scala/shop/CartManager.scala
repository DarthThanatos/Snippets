package shop

import java.net.URI

import akka.actor.{Actor, Props, Timers}
import akka.persistence.fsm.PersistentFSM
import Main.system
import communication.Item

import scala.concurrent.duration._
import scala.language.postfixOps
import scala.reflect.{ClassTag, classTag}


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

  def listOfItems : List[Item] =  items.values.toList
}

object Cart {
  val empty = Cart(Map.empty)
}

class CartManager(id: String) extends Actor with PersistentFSM[CartState, Cart, CartEvent] with Timers{

  override def persistenceId: String = "persistent-cart-fsm-id-" + id
  override def domainEventClassTag: ClassTag[CartEvent] = classTag[CartEvent]

  private var nonEmptyTimeout = TimerValues.cartTimer

  override def applyEvent(event: CartEvent, cartBeforeEvent: Cart): Cart = event match{
    case ItemRemoved(item :Item) =>
      cartBeforeEvent.removeItem(item)
    case ItemAdded(item: Item) =>
      cartBeforeEvent.addItem(item)
    case CartEmptied() => Cart.empty
  }

  startWith(Empty, Cart.empty)
  println("Actor CartManager started")

  when(Empty){
    case Event(AddItem(item:Item), cart) =>
      addItemGoingTo(item, NonEmpty, "v2: An item was added to a cart in the empty state, items count " + (cart.items.get(item.id) match {case Some(Item(_, _, _, count)) => count; case None => -1}))
  }


  when(NonEmpty, stateTimeout = TimerValues.cartTimer seconds){

    case Event(AddItem(item: Item), cart) =>
      addItemGoingTo(item, NonEmpty,"v2: An item was added to a cart in the non-empty state, items count " + (cart.items.get(item.id) match {case Some(Item(_, _, _, count)) => count; case None => -1}))

    case Event(RemoveItem(item: Item), cart) if !cart.itemsAboutToBeEmpty(item) =>
      removeItemGoingTo(item, NonEmpty, "v2: An item was removed from a cart in the non-empty state, still in the non-empty state, items count " + (cart.items.get(item.id) match {case Some(Item(_, _, _, count)) => count; case None => -1}))

    case Event(RemoveItem(item: Item), cart) =>
      emptyCart("v2: An item was removed from a cart in the non-empty state, now in the empty state, items count " + (cart.items.get(item.id) match {case Some(Item(_, _, _, count)) => count; case None => -1}))

    case Event(StartCheckout(), cart) =>
      goto(InCheckout)

    case Event(StateTimeout, _) =>
      emptyCart("v2: Cart Timer expired")

  }


  onTransition{
    case Empty -> NonEmpty =>
    case NonEmpty -> NonEmpty =>

    case NonEmpty -> InCheckout =>
      val checkout = system.actorOf(Props(new Checkout(self, id, stateData.listOfItems)))
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

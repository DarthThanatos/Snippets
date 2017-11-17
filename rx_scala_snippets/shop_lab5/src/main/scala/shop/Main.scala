package shop

import java.net.URI
import java.util.concurrent.TimeUnit

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.util.Timeout
import communication.Item
import remote.DB

import scala.concurrent.duration.FiniteDuration
import scala.util.control.Breaks._
import scala.util.{Failure, Success}

object Main {

  val system: ActorSystem = ActorSystem()
  @volatile  private var checkoutCompleted = true

  trait CaseCreatable[T]{
    def createCase(msg: String) : T
  }

  private def checkoutPhaseStep[T](promptMsg: String , checkout: ActorRef, nextStep: ActorRef => Unit, actorMsg : CaseCreatable[T]): Unit ={
    println(promptMsg)
    val checkoutData:String   = io.StdIn.readLine()
    checkout ! (if (checkoutData != "cancel") actorMsg.createCase(checkoutData) else CheckoutCancelled())
    if (checkoutData != "cancel") nextStep(checkout)

  }

  private def selectDelivery(checkout: ActorRef) {
      checkoutPhaseStep(
        "[Checkout-SelectingDelivery] Please type a delivery method or cancel to quit:",
        checkout,
        selectPayment,
        (msg: String) => SelectDelivery(msg)
      )
  }


  private def selectPayment(checkout: ActorRef) {
    checkoutPhaseStep(
      "[Checkout-SelectingPaymentMethod] Please type a payment method or cancel to quit:",
      checkout,
      processPayment,
      (msg: String) => SelectPayment(msg)
    )
  }

  private def processPayment(checkout: ActorRef) {
    checkoutPhaseStep(
      "[Checkout-ProcessingPayment] Please type payment amount you want to pay us or cancel to quit:",
      checkout,
      checkoutDone,
      (msg: String) => ReceivePayment(msg.toFloat)
    )
  }

  private def checkoutDone(checkout: ActorRef): Unit ={
    println("Checkout done")
  }

  private def startCheckout(cart: ActorRef): Unit ={
    import scala.concurrent.ExecutionContext.Implicits.global
    cart ! StartCheckout()
    Thread.sleep(500)
    checkoutCompleted = false
    implicit val timeout: Timeout = Timeout(FiniteDuration(1, TimeUnit.SECONDS))
    system.actorSelection("/user/checkout").resolveOne().onComplete{
      case Success(checkout) =>
        selectDelivery(checkout)
        checkoutCompleted = true
      case Failure(ex) =>
        println("You can only start a checkout having a non-empty cart\n" + ex.getMessage)
        checkoutCompleted = true
    }

  }

  private def addItem(cart: ActorRef): Unit ={
    cart ! AddItem(Item(new URI("food/1"), "cheese", 213, 6))
  }

  private def delItem(cart: ActorRef): Unit ={
    cart ! RemoveItem(Item(new URI("food/1"), "cheese", 213, 6))
  }


  private def printHelp(): Unit ={
    println(
      "add - add one item\n" +
      "del - remove one item\n" +
      "h - display this help\n" +
      "sch - start a checkout\n" +
      "gs - get current state\n" +
      "q - exit from the system\n"
    )
  }

  private def interact(cartActor: ActorRef): Unit = {
    breakable{
      while(true) {
        if(checkoutCompleted) {
          print("> ")
          val cmd = io.StdIn.readLine()
          cmd match {
            case "add" => addItem(cartActor)
            case "del" => delItem(cartActor)
            case "sch" => startCheckout(cartActor)
            case "h" => printHelp()
            case "gs" => cartActor ! GetState()
            case "q" => System.exit(0);
            case wtf => println("Not a valid option: " + wtf + ", type h for help")
          }
        }
      }
    }
  }


  private def interactiveMain(): Unit ={
    val cart = system.actorOf(Props[CartManager], "cart")
    interact(cart)
  }

  def testDB(): Unit ={
    val db = new DB("..\\..\\..\\..\\db\\db")
    db.printDB()
  }

  def testScoreSorting(): Unit ={
    val item = Item(new URI("a"), "a", 0, 0)
    val refItem = Item(new URI("b"), "b", 1, 1)
    val res1 = List((10, item), (5, item),(5, item),(5, item),(5, item),(5, item),(5, item),(5, item),(5, item),(5, item),(5, item),(5, item),(5, item),(5, item),(5, item),(5, item),(5, item),(5, item),(5, item),(5, item),(5, item))
    println(appendScoreToSorted(res1, refItem, 7))
  }

  def testCustomer(): Unit ={
    system.actorOf(Props[Customer], "customer")
  }


  def appendScoreToSorted(target: List[(Int, Item)], item: Item, itemScore: Int): List[(Int, Item)] ={
    val biggerVals: List[(Int,Item)] = target.filter( _._1 >= itemScore)
    val smallerVals = target.filter( _._1 < itemScore)
    (biggerVals ++ ((itemScore, item) :: smallerVals)).take(10)
  }

  def main(args: Array[String]): Unit ={
    testDB()

  }
}



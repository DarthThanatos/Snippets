package shop
package v2

import java.util.concurrent.TimeUnit

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.event.Logging
import akka.util.Timeout

import scala.concurrent.duration.FiniteDuration
import scala.util.control.Breaks._
import scala.util.{Failure, Success}

object Main {

  val system: ActorSystem = ActorSystem()
  val log = Logging(system, Main.getClass.getName)
  @volatile  private var checkoutCompleted = true

  private def selectDelivery(checkout: ActorRef) {
      println("[Checkout-SelectingDelivery] Please type a delivery method or cancel to quit:")
      val deliveryMethod:String   = io.StdIn.readLine()
      checkout ! (if (deliveryMethod != "cancel") DeliverySelected(deliveryMethod) else CheckoutCancelled())
      if (deliveryMethod != "cancel") selectPayment(checkout)
  }


  private def selectPayment(checkout: ActorRef) {
    println("[Checkout-SelectingPaymentMethod] Please type a payment method or cancel to quit:")
    val paymentMethod  = io.StdIn.readLine()
    checkout ! (if (paymentMethod != "cancel") PaymentSelected(paymentMethod) else CheckoutCancelled())
    if (paymentMethod != "cancel") processPayment(checkout)
  }

  private def processPayment(checkout: ActorRef) {
    println("[Checkout-ProcessingPayment] Please type payment amount you want to pay us or cancel to quit:")
    val paymentAmount  = io.StdIn.readLine()
    checkout ! (if (paymentAmount != "cancel") PaymentReceived(paymentAmount) else CheckoutCancelled())
  }

  private def startCheckout(cart: ActorRef): Unit ={
    import scala.concurrent.ExecutionContext.Implicits.global
    cart ! CheckoutStarted()
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
    cart ! ItemAdded()
  }

  private def delItem(cart: ActorRef): Unit ={
    cart ! ItemRemoved()
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


  def main(args: Array[String]): Unit ={
    log.debug("Initializing shop")
    val cart = system.actorOf(Props[Cart], "cart")
    interact(cart)
  }
}



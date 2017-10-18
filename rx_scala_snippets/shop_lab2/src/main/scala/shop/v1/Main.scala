package shop.v1

import akka.actor.{ActorSystem, Props}
import akka.event.Logging

object Main {


  val system: ActorSystem = ActorSystem()
  val log = Logging(system, Main.getClass.getName)

  def main(args: Array[String]): Unit ={
    log.debug("Initializing shop")
    val cart = system.actorOf(Props[Cart], "cart")
    val chekout = system.actorOf(Props[Checkout], "checkout")
    println("Hello world")
  }
}

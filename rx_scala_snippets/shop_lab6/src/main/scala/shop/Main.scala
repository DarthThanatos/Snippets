package shop

import java.net.URI
import java.util.concurrent.TimeUnit

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import communication.Item
import remote.DB
import sun.misc.ObjectInputFilter.Config

import scala.concurrent.duration.FiniteDuration
import scala.util.control.Breaks._
import scala.util.{Failure, Success}

object Main {

  private val config = ConfigFactory.load()
  val system: ActorSystem = ActorSystem("System", config.getConfig("clientapp").withFallback(config))
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
    testCustomer()

  }
}



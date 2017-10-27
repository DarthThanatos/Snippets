package shop

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}

import scala.concurrent.duration._

class CartAsynchSpec extends TestKit(ActorSystem("CartSpec"))
  with WordSpecLike with BeforeAndAfterAll with ImplicitSender  {

  val cart: ActorRef = system.actorOf(Props[Cart])

  override def afterAll(): Unit = {
    system.terminate
  }

  "A Cart" must {
    "increase the number of items inside it" in {
      cart ! ItemAdded()
      expectNoMessage(1 second)
    }

    "remain in the nonempty state" in {
      cart ! GetState()
      expectMsg(NonEmpty)

    }
  }

}
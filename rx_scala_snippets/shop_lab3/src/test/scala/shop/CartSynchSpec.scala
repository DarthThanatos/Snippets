package shop

import akka.actor.ActorSystem
import akka.testkit.{TestActorRef, TestKit}
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}

class CartSynchSpec extends TestKit(ActorSystem("CartSpec"))
  with WordSpecLike with BeforeAndAfterAll  {

  val cart: TestActorRef[Cart] =  TestActorRef[Cart]

  override def afterAll(): Unit = {
    system.terminate
  }

  "A Cart" must {
    "increase the number of items inside it" in {
      cart ! ItemAdded()
      assert (cart.underlyingActor.itemsCount == 1)
    }

    "remain in the nonempty state" in {
      assert (cart.underlyingActor.stateName == NonEmpty)

    }
  }

}
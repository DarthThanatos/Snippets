package shop

import akka.actor.Actor
import communication.Item

class PaymentService(items: List[Item]) extends Actor{

  override def receive :Receive = {

    case DoPayment =>
      println("Payment service got dopayment order")
      context.actorSelection("/user/customer") ! PaymentConfirmed
      context.parent ! ReceivePayment(11)
      context stop self

  }

}

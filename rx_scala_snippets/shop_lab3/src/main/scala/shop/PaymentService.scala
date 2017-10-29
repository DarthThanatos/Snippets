package shop

import akka.actor.Actor

class PaymentService extends Actor{
  override def receive :Receive = {

    case DoPayment =>
      println("Payment service got dopayment order")
      context.actorSelection("/user/customer") ! PaymentConfirmed
      context.parent ! PaymentReceived("11")
      context stop self
  }

}

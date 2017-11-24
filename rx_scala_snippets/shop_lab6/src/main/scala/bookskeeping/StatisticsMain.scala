package bookskeeping

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Subscribe
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn
import scala.concurrent.duration._
import akka.http.scaladsl.server.Directives._
import communication.ReceivedQuery

import scala.util.{Failure, Success}
import akka.pattern.ask

case object GetStatistic

class QueriesCounter extends Actor with ActorLogging {
  import akka.cluster.pubsub.DistributedPubSubMediator.SubscribeAck
  val mediator: ActorRef = DistributedPubSub(context.system).mediator
  private var counter: Map[String, Int] = Map()

  mediator ! Subscribe("QueryCounter", self)

  private def statistic :String =
    if (counter.nonEmpty) "<h1>" + counter.map{ entry => entry._1 + " -> " + entry._2  + "<br>" }.reduce((acc, entry) => acc + entry ) + " </h1>"
    else "No counter history"

  def receive: PartialFunction[Any, Unit] = {
    case ReceivedQuery(query: String, who: String) ⇒
      log.info("Query Counter increasing counter of {}", who)
      val counterSoFar = if (counter.contains(who)) counter(who) else 0
      counter = counter.updated(who, counterSoFar + 1)
    case SubscribeAck(Subscribe("QueryCounter", None, `self`)) ⇒
      log.info("Queries Counter subscribing")
    case GetStatistic => sender ! statistic
  }
}

class QueriesLogger extends Actor with ActorLogging{
  import akka.cluster.pubsub.DistributedPubSubMediator.SubscribeAck
  val mediator: ActorRef = DistributedPubSub(context.system).mediator

  mediator ! Subscribe("QueryLogger", self)

  def receive: PartialFunction[Any, Unit] = {
    case ReceivedQuery(query: String, who: String) ⇒
      log.info("{} got query: {}", who, query)
    case SubscribeAck(Subscribe("QueryLogger", None, `self`)) ⇒
      log.info("Queries Logger subscribing")
  }

}


object StatisticsMain {
  def main(args: Array[String]): Unit ={
    val config = ConfigFactory.load()
    implicit val system: ActorSystem = ActorSystem("RemoteSystem", config.getConfig("statisticsapp").withFallback(config))
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

    val queriesCounter = system.actorOf(Props[QueriesCounter])
    val queriesLogger = system.actorOf(Props[QueriesLogger])

    //url = http://192.168.0.107:9999/statistics
    implicit val timeout: akka.util.Timeout = 1.minutes
    val route =
      path("statistics") {
        get {
          onComplete(queriesCounter ? GetStatistic){
            case Success(res: String) => complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, res))
            case Failure(failure) => complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Failure while accessing statistics</h1>"))
          }

        }

      }

    val ip = "192.168.0.107"
    val bindingFuture = Http().bindAndHandle(route, ip, 9999)

    println(s"Statistics online at http://$ip:9999/statistics\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}

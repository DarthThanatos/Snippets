package paymentServer
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.StandardRoute
import akka.stream.ActorMaterializer
import akka.util.ByteString
import com.typesafe.config.ConfigFactory

import scala.concurrent.{Await, ExecutionContextExecutor}
import scala.concurrent.duration._
import scala.io.StdIn

object PayPal{
  def main(args: Array[String]) {
    val config = ConfigFactory.load()
    implicit val system: ActorSystem = ActorSystem("paypal-system", config.getConfig("paypal"))
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

    def parseFloat(s : String): Option[Float]={
      println("parsing " + s + " to float")
      import scala.util.Try
      Try { s.toFloat }.toOption
    }

    def printOrder(order: HttpEntity): Unit ={
      order.dataBytes.runFold(ByteString(""))(_ ++ _).foreach {
        body => println("Got payment, amount: " + body.utf8String)
      }
    }

    def optionFloatFromFuture(order: HttpEntity): Option[Float] ={
      parseFloat(
        Await.result(
          order.dataBytes.runFold(ByteString(""))(_ ++ _),
          1.minutes
        ).utf8String
      )
    }

    def completeAccordingToOption(option: Option[Float]):StandardRoute =
      option match {
        case Some(float) =>
          complete {
            HttpEntity(ContentTypes.`text/plain(UTF-8)`, "Payment received")
          }
        case None =>
          complete(400 -> "Bad non parsable argument")
      }

    def handleOrder(order: HttpEntity): StandardRoute = {
      printOrder(order)
      val res :Option[Float] = optionFloatFromFuture(order)
      completeAccordingToOption(res)
    }

    val route =
      path("payment") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
        }
        post {
          // decompress gzipped or deflated requests if required
          decodeRequest {
            // unmarshal with in-scope unmarshaller
            entity(as[ HttpEntity]) {
              order => handleOrder(order)
            }
          }
        }

      }  ~
      path("error-404"){
        post{
          complete(404 -> "You are asking for a not-found error")
        }
      } ~
      path("error-401"){
        post{
          complete(401 -> "You are asking for an authorization error")

        }
      } ~
      path("error-403"){
        post{
          complete(403 -> "You are asking for a forbidden error")
        }
      }



    val ip = "192.168.0.107"
    val bindingFuture = Http().bindAndHandle(route, ip, 80)

    println(s"Server online at http://$ip:80/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}

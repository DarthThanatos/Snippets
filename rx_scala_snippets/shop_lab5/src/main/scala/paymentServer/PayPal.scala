package paymentServer
import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.coding.Deflate
import akka.http.scaladsl.marshalling.ToResponseMarshaller
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.model.StatusCodes.MovedPermanently
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.FromRequestUnmarshaller
import akka.pattern.ask
import akka.stream.ActorMaterializer
import akka.util.{ByteString, Timeout}
import akka.http.scaladsl.server.{HttpApp, Route}
import com.typesafe.config.ConfigFactory

import scala.io.StdIn

object PayPal{
  def main(args: Array[String]) {
    val config = ConfigFactory.load()
    implicit val system = ActorSystem("paypal-system", config.getConfig("paypal"))
    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher

    val route =
      path("payment") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
        }
        post {
          // decompress gzipped or deflated requests if required
          decodeRequest {
            // unmarshal with in-scope unmarshaller
            entity(as[ HttpEntity]) { order =>

              order.dataBytes.runFold(ByteString(""))(_ ++ _).foreach { body =>
                println("Got payment, amount: " + body.utf8String)
              }
              complete {
                HttpEntity(ContentTypes.`text/plain(UTF-8)`, "Payment received")
              }
            }
          }
        }
      }


    val ip = "192.168.0.100"
    val bindingFuture = Http().bindAndHandle(route, ip, 80)

    println(s"Server online at http://$ip:80/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}

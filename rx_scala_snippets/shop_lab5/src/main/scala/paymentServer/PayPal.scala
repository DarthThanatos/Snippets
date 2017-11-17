package paymentServer
import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.coding.Deflate
import akka.http.scaladsl.marshalling.ToResponseMarshaller
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.model.StatusCodes.MovedPermanently
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.FromRequestUnmarshaller
import akka.pattern.ask
import akka.stream.ActorMaterializer
import akka.util.Timeout
import akka.http.scaladsl.server.{HttpApp, Route}

object PayPal extends HttpApp{
  override def routes :Route =
  path("Hello"){
    get{
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
    }
  }
}

object Main{
  def main(args: Array[String]): Unit ={
    PayPal.startServer("localhost", 80)
  }
}
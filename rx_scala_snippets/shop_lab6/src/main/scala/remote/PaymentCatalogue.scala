package remote

import java.io.File

import akka.actor.{Actor, ActorRef, ActorSystem, Props, Terminated}
import com.github.tototoshi.csv._
import communication.{Answer, Item, Query, ReceivedQuery}

import scala.util.{Failure, Random, Success}
import java.net.URI

import akka.cluster.pubsub.DistributedPubSub
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration._
import scala.io.StdIn
import spray.json._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives
import akka.pattern.ask
import akka.routing.{ActorRefRoutee, RoundRobinRoutingLogic, Router}

class DB(pathToDB : String){
  private val DEFAULT_RESULT_SIZE = 10
  private val MAX_PRICE = 1000
  private val MAX_COUNT = 150

  private case class ItemProducedBy(item: Item, company: String)

  private val reader = CSVReader.open(new File(pathToDB))
  private val csvStorage = reader.all()
  private val storage : List[ItemProducedBy] =
    for (List(uri, name, company) <- csvStorage) yield{
      val item = Item(new URI(uri), name, math.abs(new Random().nextInt()) % MAX_PRICE, math.abs(new Random().nextInt())% MAX_COUNT)
      ItemProducedBy(item, company)
    }

  private def calcScore(wordsInQuery: List[String], stringMatchedAgainst: String): Int ={
    var res : Int = 0
    val wordsInMatchedString = stringMatchedAgainst.split(" ")
    for(wordInQuery <- wordsInQuery){
      res += (if(wordsInMatchedString contains wordInQuery) 1 else 0)
    }
    res
  }

  private def appendScoreToSorted(target: List[(Int, Item)], item: Item, itemScore: Int): List[(Int, Item)] ={
    val biggerVals: List[(Int,Item)] = target.filter( _._1 >= itemScore)
    val smallerVals = target.filter( _._1 < itemScore)
    (biggerVals ++ ((itemScore, item) :: smallerVals)).take(DEFAULT_RESULT_SIZE)
  }

  def search(query: String): List[Item] = {
    val wordsInQuery = query.split(" ").toList
    var scores = List[(Int, Item)]()
    for(ItemProducedBy(item @ Item(_, name, _, _), company) <- storage){
      val score = calcScore(wordsInQuery, name) + calcScore(wordsInQuery, company)
      scores = appendScoreToSorted(scores, item, score)
    }
    scores.map { case (score, item) => item}
  }


  def printDB(): Unit = {
    storage foreach println
    println("DB size: " + storage.size)
  }

}

class DBSearcher extends Actor{

  override def receive :Receive = {
    case SearchDB(query, db) =>
      val res = db.search(query)
      sender() ! Answer(res)
  }
}

case class SearchDB(query : String, db: DB)

class PaymentCatalogue(pathToDB : String) extends  Actor {
  import akka.cluster.pubsub.DistributedPubSubMediator.Publish
  // activate the extension
  val mediator: ActorRef = DistributedPubSub(context.system).mediator

  private val db: DB = new DB(pathToDB)
  println("Started " + self.path.address + self.path.toSerializationFormat)
  val ACTOR_POOL_AMOUNT: Int = 5

  var router: Router = {
    val routees = Vector.fill(ACTOR_POOL_AMOUNT) {
      val r = context.actorOf(Props[DBSearcher])
      context watch r
      ActorRefRoutee(r)
    }
    Router(RoundRobinRoutingLogic(), routees)
  }


  override def receive :Receive ={
    case  Query(query: String) =>
      router.route(SearchDB(query, db), sender())
      mediator ! Publish("QueryLogger", ReceivedQuery(query, PaymentCatalogueMain.ip))
      mediator ! Publish("QueryCounter", ReceivedQuery(query, PaymentCatalogueMain.ip))

    case Terminated(a) ⇒
      router = router.removeRoutee(a)
      val r = context.actorOf(Props[DBSearcher])
      context watch r
      router = router.addRoutee(r)

  }
}

object PaymentCatalogueMain extends Directives with SprayJsonSupport with DefaultJsonProtocol   {
  val ip = "192.168.0.101"

  def main(args: Array[String]): Unit ={
    val config = ConfigFactory.load()
    implicit val system: ActorSystem = ActorSystem("RemoteSystem", config.getConfig("serverapp").withFallback(config))
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

    val paymentCatalogue = system.actorOf(Props(new PaymentCatalogue("..\\..\\..\\..\\db\\db")), "PaymentCatalogue")

    implicit object URIFormat extends JsonFormat[URI] {
      def write(obj: URI) = JsString(obj.toString)

      def read(json: JsValue): URI = json match {
        case JsString(uri) => new URI(uri)
        case other => deserializationError("Expected URI, got: " + other)
      }
    }
    implicit val itemFormat: RootJsonFormat[Item] = jsonFormat4(Item)
    implicit val printer: PrettyPrinter = PrettyPrinter

    //url = http://192.168.0.107:8080/items?query=Nurturme
    implicit val timeout: akka.util.Timeout = 1.minutes
    val route =
      path("items") {
        get {
          parameters('query) {
            query =>
                onComplete(paymentCatalogue ? Query(query)){
                  case Success(result: Answer)  ⇒ complete(result.itemsAvailable)
                  case Failure(failure) ⇒ complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Failure</h1>"))
                }

          }
        }

      }

    val bindingFuture = Http().bindAndHandle(route, ip, 8080)

    println(s"Payment Catalogue online at http://$ip:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}

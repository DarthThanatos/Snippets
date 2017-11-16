package remote

import java.io.File

import akka.actor.{Actor, ActorRef, Props}
import com.github.tototoshi.csv._
import communication.{Answer, Item, Query}

import scala.util.Random
import java.net.URI

class DB(pathToDB : String){
  private val DEFAULT_RESULT_SIZE = 10
  private val MAX_PRICE = 1000
  private val MAX_COUNT = 150

  private val reader = CSVReader.open(new File(pathToDB))
  private val csvStorage = reader.all()
  private val storage =
    for (List(uri, name, _*) <- csvStorage) yield{
      Item(new URI(uri), name, math.abs(new Random().nextInt()) % MAX_PRICE , math.abs(new Random().nextInt())% MAX_COUNT)
    }

  def search(query: String): List[Item] = ???


  def printDB(): Unit = {
    storage foreach println
    println("DB size: " + storage.size)
  }

}

class DBSearcher extends Actor{

  override def receive :Receive = {
    case SearchDB(query, db, customer) =>
      val res = db.search(query)
      sender() ! SearchDone(res, customer)
  }
}


case class SearchDB(query : String, db: DB, customer: ActorRef)
case class SearchDone(items : List[Item], customer: ActorRef)

class PaymentCatalogue(pathToDB : String) extends  Actor{
  private val db: DB = new DB(pathToDB)

  override def receive :Receive ={
    case  Query(query: String) =>
      val searcher = context.actorOf(Props[DBSearcher])
      searcher ! SearchDB(query, db, sender())

    case SearchDone(items, customer) =>
      customer ! Answer(items)
  }
}

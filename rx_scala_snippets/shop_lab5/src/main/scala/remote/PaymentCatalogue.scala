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

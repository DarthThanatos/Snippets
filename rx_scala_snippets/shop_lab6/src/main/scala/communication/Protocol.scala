package communication

import java.net.URI

import akka.http.scaladsl.model.RequestEntity

/**
  * @param id: unique item identifier (java.net.URI)
  */
case class Item(id: URI, name: String, price: BigDecimal, count: Int)
case class Query(query: String)
case class ReceivedQuery(query: String, who: String)
case class Answer(itemsAvailable: List[Item])

case class HttpException(failureCode: Int, reason: String) extends RuntimeException(reason)

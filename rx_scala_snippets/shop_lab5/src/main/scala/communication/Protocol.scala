package communication

import java.net.URI

/**
  * @param id: unique item identifier (java.net.URI)
  */
case class Item(id: URI, name: String, price: BigDecimal, count: Int)

case class Query(query: String)
case class Answer(itemsAvailable: List[Item])
package shop

trait CartEvent
case class ItemAdded(item:Item) extends CartEvent
case class ItemRemoved(item: Item) extends CartEvent
case class CartEmptied() extends CartEvent
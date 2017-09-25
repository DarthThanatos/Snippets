
object Appl470 {
  object WeekDay extends Enumeration {
    type WeekDay = Value
    val Mon, Tue, Wed, Thu, Fri, Sat, Sun = Value
  }
  import WeekDay._
  def printDayName(day: WeekDay) = {
    day match {
	
      case _   => println("What the he..?!")
      case Mon => println("Monday")
      case Tue => println("Tuesday")
      case Wed => println("Wednesday")
      case Thu => println("Thursday")
      case Fri => println("Friday")
      case Sat => println("Saturday")
      case Sun => println("Sunday")
    }
  }
  def main(args: Array[String]) {
    for (day <- WeekDay.values) printDayName(day)
  }
}
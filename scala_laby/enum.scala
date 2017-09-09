object Weekdays extends Enumeration{
	type Weekdays = Value
	val Mon, Tue = Value
}

import Weekdays._

object Enum extends App{
	def printday(s : Weekdays) = s match{
		case Mon => println("Monday")
		case _ => println("Fuck me")
	}
	
	printday(Mon)
	printday(Tue)
}

def matching(s: Any) = s match{
case s : String => println("String matched")
case 1.1 => println("1.1 matched")
case s: Array[_] => println("Array matched, a = " + s)
}
import DefaultNumberLikes._
import JodaImplicits._
import org.joda.time.Duration._


object TypeClassMain{
	def main(args: Array[String]){
		val numbers = 
			Vector[Double](13, 23.0, 42, 45, 61, 73, 96, 100, 199, 420, 900, 3839)

		println(Statistics.mean(numbers))

		val durations = Vector(standardSeconds(20), standardSeconds(57), standardMinutes(2),
		  standardMinutes(17), standardMinutes(30), standardMinutes(58), standardHours(2),
		  standardHours(5), standardHours(8), standardHours(17), standardDays(1),
		  standardDays(4))

		println(Statistics.mean(durations).getStandardHours)
	}
}
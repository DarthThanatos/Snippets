package simulation
import scala.actors._
import scala.actors.Actor._

trait Simulant extends Actor {

	val clock: Clock
	def handleSimMessage(msg: Any)
	def simStarting() { }

	def act() {
		loop {
			react {
				case Stop => exit()
				case Ping(time) =>
					if (time == 1) simStarting()
					clock ! Pong(time, self)
				case msg => handleSimMessage(msg)
			}
		}
	}

	start()
}

package simulation
package circuit

import scala.actors._

class Wire(val clock:Clock, name: String, init: Boolean) extends Simulant {

	val WireDelay = 1
	def this(clock:Clock, name: String) { this(clock, name, false) }
	def this(clock:Clock) { this(clock, "unnamed") }
	
	// val clock = Circuit.clock
	clock.add(this)

	private var sigVal = init
	private var observers: List[Actor] = List()

	def handleSimMessage(msg: Any) {
		msg match {
			case SetSignal(s) =>
				if (s != sigVal) {
					sigVal = s
					signalObservers()
				}
		}
	}
	
	def signalObservers() {
		for (obs <- observers)
			clock ! AfterDelay(
				WireDelay,
				SignalChanged(this, sigVal),
				obs
			)
	}

	override def simStarting() { signalObservers() }
	
	def addObserver(obs: Actor) {
		observers = obs :: observers
	}

	override def toString = "Wire("+ name +")"
}
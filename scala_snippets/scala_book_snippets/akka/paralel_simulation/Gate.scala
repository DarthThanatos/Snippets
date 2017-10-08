package simulation
package circuit

import scala.actors._

abstract class Gate(val clock:Clock, in1: Wire, in2: Wire, out: Wire) extends Simulant {

	def computeOutput(s1: Boolean, s2: Boolean): Boolean
	val delay: Int

	val InverterDelay = 2
	val OrGateDelay = 3
	val AndGateDelay = 3

	// val clock = Circuit.clock
	clock.add(this)

	in1.addObserver(this)
	in2.addObserver(this)

	var s1, s2 = false
	def handleSimMessage(msg: Any) {
		msg match {
			case SignalChanged(w, sig) =>
				if (w == in1)
					s1 = sig
				if (w == in2)
					s2 = sig
				clock ! 
					AfterDelay(
						delay,
						SetSignal(computeOutput(s1, s2)),
						out
					)
		}
	}

}

object Gate{
	
	def orGate(clock:Clock,in1: Wire, in2: Wire, output: Wire) =
		new Gate(clock, in1, in2, output) {
			val delay = OrGateDelay
			def computeOutput(s1: Boolean, s2: Boolean) = s1 || s2
		}

	def andGate(clock:Clock,in1: Wire, in2: Wire, output: Wire) =
		new Gate(clock, in1, in2, output) {
			val delay = AndGateDelay
			def computeOutput(s1: Boolean, s2: Boolean) = s1 && s2
		}

	private class DummyWire(clock:Clock) extends Wire(clock, "dummy")

	def inverter(clock:Clock, input: Wire, output: Wire) =
		new Gate(clock, input, new DummyWire(clock), output) {
			val delay = InverterDelay
			def computeOutput(s1: Boolean, ignored: Boolean) = !s1
		}

}
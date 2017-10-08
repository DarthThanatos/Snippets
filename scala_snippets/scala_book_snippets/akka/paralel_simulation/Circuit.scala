package simulation
package circuit

import scala.actors._

case class SetSignal(sig: Boolean)
case class SignalChanged(wire: Wire, sig: Boolean)

class Circuit(clock: Clock){

	// val clock = new Clock
	
	def probe(wire: Wire) = new Simulant {
		val clock = Circuit.this.clock
		clock.add(this)
		wire.addObserver(this)
		def handleSimMessage(msg: Any) {
			msg match {
				case SignalChanged(w, s) =>
					println("signal "+ w +" changed to "+ s)
			}
		}
	}

	def start() { clock ! Start }
}

object Circuit{	
}

trait Adders extends Circuit {
	

	import Gate._

	def halfAdder(clock:Clock,a: Wire, b: Wire, s: Wire, c: Wire) {
		val d, e = new Wire(clock)
		orGate(clock, a, b, d)
		andGate(clock, a, b, c)
		inverter(clock, c, e)
		andGate(clock, d, e, s)
	}

	def fullAdder(clock:Clock,a: Wire, b: Wire, cin: Wire, sum: Wire, cout: Wire) {
		val s, c1, c2 = new Wire(clock)
		halfAdder(clock, a, cin, s, c1)
		halfAdder(clock, b, s, sum, c2)
		orGate(clock, c1, c2, cout)
	}
}
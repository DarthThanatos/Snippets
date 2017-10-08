import simulation._
import simulation.circuit._

object Demo {
	def main(args: Array[String]) {
		val clock = new  Clock
		val circuit = new Circuit(clock) with Adders
		import circuit._
		val ain = new Wire(clock,"ain", true)
		val bin = new Wire(clock,"bin", false)
		val cin = new Wire(clock,"cin", true)
		val sout = new Wire(clock,"sout")
		val cout = new Wire(clock, "cout")
		probe(ain)
		probe(bin)
		probe(cin)
		probe(sout)
		probe(cout)
		fullAdder(clock, ain, bin, cin, sout, cout)
		println("Starting simulation")
		circuit.start()
	}
}

package simulation

import scala.actors._
import scala.actors.Actor._

case class Ping(time: Int)
case class Pong(time: Int, from: Actor)

case object Start
case object Stop
case class AfterDelay(delay: Int, msg: Any, target: Actor)
case class WorkItem(time: Int, msg: Any, target: Actor)

class Clock extends Actor{

	private var running = false
	private var currentTime = 0


	// type Action = () => Unit
	private var agenda: List[WorkItem] = List()
	
	private var allSimulants: List[Actor] = List()
	private var busySimulants: Set[Actor] = Set.empty

	def add(sim: Simulant) {
		allSimulants = sim :: allSimulants
	}
	def act() {
		loop {
			if (running && busySimulants.isEmpty)
				advance()
			reactToOneMessage()
		}
	}

	def advance() {
		if (agenda.isEmpty && currentTime > 0) {
			println("** Agenda empty. Clock exiting at time "+ currentTime+".")
			self ! Stop
			return
		}
		currentTime += 1
		println("Advancing to time "+currentTime)
		processCurrentEvents()
		for (sim <- allSimulants)
			sim ! Ping(currentTime)
		busySimulants = Set.empty ++ allSimulants
	}

	private def processCurrentEvents() {
		val todoNow = agenda.takeWhile(_.time <= currentTime)
		agenda = agenda.drop(todoNow.length)
		for (WorkItem(time, msg, target) <- todoNow) {
			assert(time == currentTime)
			target ! msg
		}
	}

	def reactToOneMessage() {
		react {
			case AfterDelay(delay, msg, target) =>
				val item = WorkItem(currentTime + delay, msg, target)
				agenda = insert(agenda, item)
			case Pong(time, sim) =>
				assert(time == currentTime)
				assert(busySimulants contains sim)
				busySimulants -= sim
			case Start => 
				running = true
			case Stop =>
				for (sim <- allSimulants)
				sim ! Stop
				exit()
		}
	}

	private def insert(ag: List[WorkItem], item: WorkItem): List[WorkItem] = {
		if(ag.isEmpty || item.time < ag.head.time) item :: ag 
		else ag.head :: insert(ag.tail, item)
	}

}
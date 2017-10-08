import scala.actors._
import scala.actors.Actor._

object SillyActor extends Actor{
	
	def act(){
		for (i <- 1 to 5) {
			println("I'm acting!")
			Thread.sleep(1000)
		}
	}

}

val echoActor = actor{
	while(true){
		receive{
			case msg => println("received message " + msg)
		}
	}
}

SillyActor.start()
echoActor ! "hi there "
echoActor ! 15
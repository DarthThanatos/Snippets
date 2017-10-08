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

import scala.util.control.Breaks._

val echoActor = actor{
	breakable{
		while(true){
			receive{
				case "EXIT" => println("echoActor exiting"); break 
				case msg => println("received message " + msg)
			}
		}
	}
}

SillyActor.start()
echoActor ! "hi there "
echoActor ! 15
echoActor ! "EXIT"

import java.net.{InetAddress, UnknownHostException}

trait NameResolver extends Actor{
	def act(): Unit 

	def getIp(name: String): Option[InetAddress] = {
		try {
			Some(InetAddress.getByName(name))
		} catch {
			case _:UnknownHostException => None
		}
	}
}

object NameResolverRec extends NameResolver {
	def act() {
		react {
			case (name: String, actor: Actor) =>
				actor ! getIp(name)
				act()
			case "EXIT" =>
				println("Name resolver rec exiting.")
				// quit
			case msg =>
				println("Unhandled message: "+ msg)
				act()
		}
	}

}

object NameResolverLoop extends NameResolver{
	def act() {
		loop {
			react {
				case "EXIT" =>
					println("Name resolver loop exiting.")
					break // loop catches this exception
				case (name: String, actor: Actor) =>
					actor ! getIp(name)
				case msg =>
					println("Unhandled message: " + msg)
			}
		}
		
	}

}

val ip = "ithanatos.hopto.org"
val scalaip = "www.scala-lang.org"

NameResolverRec.start()
NameResolverRec ! (ip, self)
NameResolverRec ! (scalaip, self)
NameResolverRec ! "EXIT"
self.receiveWithin(1000) { case x => println(x) }
self.receiveWithin(0) { case x => println(x) }


NameResolverLoop.start()
NameResolverLoop ! (ip, self)
NameResolverLoop ! (scalaip, self)
NameResolverLoop ! "EXIT"
self.receiveWithin(1000) { case x => println(x) }
self.receiveWithin(0) { case x => println(x) }

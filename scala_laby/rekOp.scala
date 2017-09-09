object ApplOp extends App{
	def wrap(s: String) = s match {
	  case s if (s != null) => Some(s)
	  case _ => None
	}
	 
	import scala.language.implicitConversions
	implicit def unwrap(opt: Option[String]) = opt match {
	  case Some(s) => s
	  case None => ""
	}

}


def wrap(s: String) = s match {
  case s if (s != null) => Some(s)
  case null => None
}
 
import scala.language.implicitConversions
implicit def unwrap(opt: Option[String]) = opt match {
  case Some(s) => s
  case None => ""
}


val a1 = Array("s1", "s2", null, "s3")
val a1Safe = for (s <- a1) yield wrap(s)
val s1Lengts2 = for (s <- a1Safe) yield s.length


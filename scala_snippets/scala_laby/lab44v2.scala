
object Appl44 {
  def sumArrayRec2(elems: Array[Int]) = {
    def goFrom(i: Int, size: Int, elms: Array[Int]): Int = {
      if (i < size) elms(i) + goFrom(i + 1, size, elms)
      else 0
    }
    goFrom(0, elems.size, elems)
  }
 
  def wrap(s: String) = s match {
	case s if (s != null) => Some(s)
	case _ => None
  }

 import scala.language.implicitConversions
 implicit def unwrap(opt: Option[String]) = opt match {
	case Some(s) => s
	case None => ""
 }

  def main(args: Array[String]) {
    //println("sumArrayRec2 = " + sumArrayRec2((0 until 5000).toArray))
	val a1 = Array("s1", "s2", null, "s3")
	val a1Safe = for (s <- a1) yield wrap(s)
	val s1Lengts1 = for (s <- a1Safe) yield unwrap(s).length
	val s1Lengts2 = for (s <- a1Safe) yield s.length
	for (i <- s1Lengts2) print(i + " ")
  }
}
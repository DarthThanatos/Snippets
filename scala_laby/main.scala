/*object Appl{
	def readFile (fileName : String) = 
		try{
			println ("Opening the file")
			val inFile = scala.io.Source.fromFile(fileName)
			try {
				for (line <- inFile.getLines) println(line)
				val x = 100 / inFile.getLines.length
			}finally{
				println("Closing the file")
				inFile.close
			}
		}catch{
			case ex: java.io.FileNotFoundException => println(ex.getMessage)
			case ex: Throwable => println("Default exceptin handler" + ex.getMessage)
		}
	def main(args: Array[String]){
		readFile("logins.txt")
	}
}
*/

/*
package p1.p2.p3
class A3
*/
/*
package p1
package p2
package p3
class A3
*/
/*
package p1.p2.p3{
class A3
}
*/
/*
package p1 {
  package p2 {
    package p3 {
      class A3
    }
  }
}
*/
//-------------------------------------------
 /*
 package p1 {
 class A1
 package p2 {
   class A21
 }
}

package p1{
	package p2 {
		class A22 { new A1 }
	}
}
*/
/*
package p1 {
  package p2 {
    class A1 { println("p1.p2.A1") }
    class A22 {
      println("Calling new p1.p2.A1") 
      new A1
      println("Calling new _root_.p1.A1")
      new _root_.p1.A1
      println("p1.p2.A22")
    }
  }
}
 
object Appl {
  def main(args: Array[String]) {
    new p1.A1
    new p1.p2.A21
    new p1.p2.A22
  }
}
*/


/*
import p1.p2.C2._
object Appl {
  import p1._
  new C1
  import p2.{C21, C22=>MyC22, C23=>_,C2 => _}
  new C21
  //new C22
  new MyC22
  //new C23
  def main(args: Array[String]) {
    m1OC2()
    m2OC2(new C1) 
  }
}
*/
//-------------------------------------
/*
object Appl {
  def main(args: Array[String]) {
    import utils._ 
	val outFile = new java.io.PrintWriter("login-passwds.txt")
	val inFile = scala.io.Source.fromFile("logins.txt")
	for (line <- inFile.getLines) {
		outFile.println(line + " : " + Passwd.nextPasswd(7))
	}
	outFile.close
  }
}
*/

/*
object ThreeColors extends Enumeration {
  type ThreeColors = Value
  val Blue, White, Red = Value
}
 
import ThreeColors._
 
object utils {
  def leadingActor(part: ThreeColors) = part match {
      case Blue => "Juliette Binoche"
      case White => "Zbigniew Zamachowski"
      case Red => "Irene Jacob"
  }
}
object Appl {
  def main(args: Array[String]) {
    for (part <- ThreeColors.values) println(part.toString() + ": " + utils.leadingActor(part))
  }
}

*/

/*
object TrafficLights {
 object TrColor extends Enumeration {
   type TrColor = Value
   val Red, RedYellow, Green, Yellow = Value
 }
 import TrColor._
 var currentColor: TrColor = Red
 def changeTo(trColor: TrColor) = {
   println("Changing to " + trColor.toString + 
           "(" + trColor.id + ")")
   trColor match {
     case Red => println("toRed handler...")
     case RedYellow => println("toRedYellow handler...") 
     case Green => println("toGreen handler...") 
     case Yellow => println("toYellow handler...") 
   }
   currentColor = trColor
 }
}
 
object Appl { 
 import TrafficLights._
  
 def main(args: Array[String]) {
   changeTo(TrColor.Red)
   for (color <- TrColor.values) {
     changeTo(color)
   }
 }
}
*/
//do 10 cwiczenia enum - value u gory jest inne niz value na dole sprawdzic

/*
class Int2DVec( var x: Int, var y: Int)
{
	override def equals(other: Any): Boolean = {
    other match {
      case that: Int2DVec => (this.x == that.x) && (this.y == that.y)
      case _ => false
    }
  }
  override def hashCode = 41 * (41 + x) + y
}

 
object Appl {
  def checkPredicate(pred: Boolean, predAsString: String) {
      if (pred) println(predAsString + ": OK")
      else println(predAsString + ": Failed")
  }
  def main(args: Array[String]) {
    val v1 = new Int2DVec(4, 5)
    val v2 = new Int2DVec(4, 5)
	val v3 = v1
	//v3.x = 3
    checkPredicate(v1 equals v2, "v1 equals v2")
    checkPredicate(v1 eq v2, "!(v1 eq v2)") //referencje i fieldy
    checkPredicate(v3 == v1, "v1 == v2") // referencje
  }
}
*/
class C1 extends T1{
	def f(i: Int) = { println(i) }
}

object Appl {
  def main(args: Array[String]) {
    (new C1).f(3)
  }
}

/*
object Appl{
	def readFile(fileName : String){
		try{
			val inFile = scala.io.Source.fromFile(fileName)
			for (line <- inFile.getLines() ) println(line)
			try{
				val x = 100/inFile.getLines().length
			}finally{
				println("closing file")
				inFile.close()
			}
		}
		catch{
			case e: java.io.FileNotFoundException => println(e.getMessage())
			case e1: Any => println("Throwable: " + e1.getMessage())
		}
	}
	def main(a:Array[String]){
		readFile("logins.txt")
	}
}
*/
/*
class C1(val x: Int)
class SubC1(x: Int) extends C1(x)
*/
/*
class C1(val x: Int)
case class SubC1(x: Int) extends C1(x)
*/
/*
class C1(val x: Int)
case class SubC1(y: Int) extends C1(y)
*/
/*
case class C1(x: Int)
class SubC1(x: Int) extends C1(x)
*/
/*
case class C1(x: Int)
case class SubC1(x: Int) extends C1(x)
*/


object Appl extends App{
	(new C1).f(3)
} 

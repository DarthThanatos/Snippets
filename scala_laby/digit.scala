object Appl{
	val hexa = scala.collection.mutable.Map[String,String]()
	def fillMap(){
		for (i <- 0 to 15)
			if (i<10) hexa(i.toString) = i.toString
			else hexa(i.toString) = (55 + i).asInstanceOf[Char].toString
	}
	def Fib(N : Int) {
		var a : Int = 0
		var b : Int = 1
		for (i <- 1 to N){
			println( b + " " + bin(b) + " " + hex(b))
			var c = b
			b = a + c
			a = c
		}
	}
	def bin(N : Int) : String={
		if (N == 0) ""
		else bin(N/2) ++ (N%2).toString
	}
	
	def hex(N : Int) : String ={
		if (N == 0) ""
		else hex(N/16) ++ hexa((N%16).toString)
	}
	
	def main(arg: Array[String]){
		val N : Int = 30
		fillMap()
		Fib(N)
	}
}
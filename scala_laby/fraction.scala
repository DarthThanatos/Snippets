import scala.beans.BeanProperty

trait Fraction {
	var num: Int
	var denom: Int
	def *(other: Fraction) : Fraction
	def /(other: Fraction) : Fraction
	def +(other: Fraction) : Fraction
	def -(other: Fraction) : Fraction
	def work( v : Int) : List[Int]
}

trait Loggable {
	def log(timeStamp: Long, msg: String) = println("[" + timeStamp + "]: " + msg)
}

trait Simplifiable{
	def fillPrime_rek(num : Int, start : Int, A : Array[Int]) : List[Int]={
		if (start > num){
			List()
		}
		else{
			if (start == num){
				val Res = List()
				if (A(start) != -1) Res :+ A(start)
				else Res 
			}
			else{
				for (i <- (start + start) to num by start; if (A(start) != -1 )) A(i) = -1
				if (A(start) != -1) fillPrime_rek(num,start + 1,A) :+ A(start)
				else fillPrime_rek(num,start + 1, A)
			}
		}
	}
	def fillPrime(num : Int) : List[Int]={
		var Res : List[Int] = List()
		val A = (0 to num ).toArray
		for (i <- 2 to num; if (A(i) != -1)){
			for (j <- i+i to num by i)
				A(j) = -1
			 Res = Res :+ i
		}
		Res
	}

	def simplestForm(f3 : Fraction) = {
		for(i <- 2 to math.min(f3.num,f3.denom)) {
			while( f3.num % i == 0 && f3.denom % i == 0  ) {
				f3.num = (f3.num / i)
				f3.denom = (f3.denom / i)
			}
		}
		f3
	}
}
  
object Fraction {
	// one of the "creational patterns/idioms"
	def apply(num: Int, denom: Int, additional_features: Boolean = false): Fraction =
		if (additional_features) new LoggableImpl(num, denom) else new DefaultImpl(num, denom) 
  
	private class DefaultImpl( override var num: Int,override var denom: Int) extends Fraction {
		override def work( v : Int) : List[Int] = List(0)
		override def *(other: Fraction): Fraction =
			Fraction(this.num * other.num, this.denom * other.denom)
		override def toString() = if (num!= denom && num != 0 && denom != 1)num.toString + "/" + denom.toString else num.toString
		override def /(other: Fraction):Fraction = {
			Fraction(this.num*other.denom, this.denom * other.num)
			}
		override def +(other: Fraction) : Fraction = 
			Fraction(this.num*other.denom + other.num*this.denom,this.denom * other.denom)
		override def -(other: Fraction) : Fraction = 
			Fraction(this.num*other.denom - other.num*this.denom,this.denom * other.denom)
	}

	private class LoggableImpl(num: Int, denom: Int) extends DefaultImpl(num, denom) with Loggable with Simplifiable{ 
		def timeStamp = System.nanoTime // to keep the example short...
		override def work( v : Int ) = fillPrime(v)
		override def *(other: Fraction): Fraction = {
			log(timeStamp, "multiplying " + this.toString + " by " + other.toString)
			val A = Fraction(this.num * other.num, this.denom * other.denom, true) // super.*(other) is not loggable
			val Res = simplestForm(A)
			Res
		}
		override def /(other : Fraction) : Fraction ={
			log(timeStamp,"dividing" + this + " by " + other)
			val A = Fraction(this.num * other.denom, this.denom * other.num)
			val Res =simplestForm(A)
			Res
		}
		override def +(other: Fraction) : Fraction = {
			log(timeStamp,"adding " + this + " by " + other)
			val A = Fraction(this.num*other.denom + other.num*this.denom,this.denom * other.denom)
			val Res = simplestForm(A)
			Res
		}
		override def -(other: Fraction) : Fraction = {
			log(timeStamp,"subtracking " + this + " by " + other)
			val A = Fraction(this.num*other.denom - other.num*this.denom,this.denom * other.denom)
			val Res = simplestForm(A)
			Res
		}
	 }
}

object Appl {
	def printTable(A:Array[Array[Fraction]], n:Int, m:Int){
		for (i<- 0 to (n - 1)) {
			for (j<- 0 to (m-1))
				print(A(i)(j) + " ")
			println("")
		}
		println(" ")
	}
	def simplestForm(f3 : Fraction) = {
			val min = math.min(math.abs(f3.num),math.abs(f3.denom))
			for(i <- 2 to min ) {
				while( f3.num % i == 0 && f3.denom % i == 0  ) {
					f3.num = (f3.num / i)
					f3.denom = (f3.denom / i)
				}
			}
			if ( (f3.num < 0 && f3.denom < 0) || (f3.num > 0 && f3.denom < 0) ) {
				f3.num = -f3.num
				f3.denom = - f3.denom
			}
			f3
		}
		
	def firstStep(A : Array[Array[Fraction]], n : Int, m: Int) = {
		var vert = 0
		for ( i <- 0 to (n-1)){
			if (A(i)(vert).num != 1 || A (i)(vert).denom!=1){
				val M = Fraction(A(i)(vert).num,A(i)(vert).denom)
				for (j <- 0 to (m-1) ){
					A(i)(j) = simplestForm(A(i)(j) /M)
				}
			}
			printTable(A,n,m)
			for ( q <- (i + 1) to (n-1); if (q <= n-1 && A(q)(vert).num != 0)){
				val M = Fraction(A(q)(vert).num,A(q)(vert).denom)
				for (j <- 0 to (m-1) ){
					val U = M*A(i)(j)
					A(q)(j) = simplestForm(  A(q)(j) - U  )
				}
			}
			printTable(A,n,m)
			vert = vert + 1
		}
		A
	}
	
	def secondStep(A : Array[Array[Fraction]], n : Int, m: Int) = {
		var vert = 1
		for ( i <- 0 to (n-2) ){
			var step = 1
			for (j<- vert to m-2){
				val M = A(i)(j)
				if (A(i)(j).num != 0){
					for (q <- j to m-1)
						A(i)(q) = simplestForm(A(i)(q) - A(i+step)(q) * M)
				}
				step = step + 1
			}
			vert = vert + 1
		}
		A
	}
	
	def Gauss_algorithm(A : Array[Array[Fraction]], n : Int, m: Int)= {
		val B = firstStep(A,n,m)
		secondStep(B,n,m)
	}
	def main(agrs: Array[String]) {
		val n = 3
		val m = 4
		val A = Array.ofDim[Fraction](n,m)
		/*
		A(1)(0) = Fraction(0,2)
		A(1)(1) = Fraction(4,1)
		A(1)(2) = Fraction(-6,1)
		A(1)(3) = Fraction(2,1)
		A(0)(0) = Fraction(3,1)
		A(0)(1) = simplestForm(Fraction(-2,1))
		A(0)(2) = simplestForm(Fraction(1,1))
		A(0)(3) = Fraction(0,1)
		A(2)(0) = simplestForm(Fraction(2,1))
		A(2)(1) = simplestForm(Fraction(-1,1))
		A(2)(2) = simplestForm(Fraction(-3,1))
		A(2)(3) = Fraction(-3,1)
		*/
		/*
		A(0)(0) = simplestForm(Fraction(1,1))
		A(0)(1) = simplestForm(Fraction(1,1))
		A(0)(2) = simplestForm(Fraction(-1,1))
		A(0)(3) = simplestForm(Fraction(2,1))
		
		A(1)(0) = simplestForm(Fraction(-5,1))
		A(1)(1) = simplestForm(Fraction(1,1))
		A(1)(2) = simplestForm(Fraction(-67,1))
		A(1)(3) = simplestForm(Fraction(-4,1))
		
		A(2)(0) = simplestForm(Fraction(2,1))
		A(2)(1) = simplestForm(Fraction(0,1))
		A(2)(2) = simplestForm(Fraction(21,1))
		A(2)(3) = simplestForm(Fraction(1,1))		
		*/
		
		A(0)(0) = simplestForm(Fraction(-2,1))
		A(0)(1) = simplestForm(Fraction(2,1))
		A(0)(2) = simplestForm(Fraction(0,1))
		A(0)(3) = simplestForm(Fraction(0,1))
		
		A(1)(0) = simplestForm(Fraction(2,1))
		A(1)(1) = simplestForm(Fraction(-4,1))
		A(1)(2) = simplestForm(Fraction(2,1))
		A(1)(3) = simplestForm(Fraction(0,1))
		
		A(2)(0) = simplestForm(Fraction(0,1))
		A(2)(1) = simplestForm(Fraction(0,1))
		A(2)(2) = simplestForm(Fraction(0,2))
		A(2)(3) = simplestForm(Fraction(0,4))
		
		printTable(A,n,m)
		val B = Gauss_algorithm(A,n,m)
		printTable(B,n,m )
		for (i <- 0 to n-1){
			println("X" + i + " = " + B(i)(m-1))
		}
		
		//val B = simplestForm(Fraction(26915438,24987337))
		//print(B)
	}
}
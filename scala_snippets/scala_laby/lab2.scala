import scala.beans.BeanProperty

class Person(id : String, val givenName : String, @BeanProperty var surname : String){
	def getName() = givenName + " " + surname
}

object Appl extends App{
	val p = new Person("1232","Jan","Kowalski")
	println(p.getName())
	p.setSurname("Bielas")
	println(p.getName())
}

class Int2DVec ( val x : Int, val y : Int){
	def +(other: Int2DVec) = new Int2DVec(this.x + other.x, this.y + other.y)
	def +( other: (Double,Double)) = (x + other._1,y + other._2)
	def |-?(other: Int2DVec) = if(x*other.x + y*other.y == 0) true else false 
	def dodaj(other: (Double,Double)) = (x + other._1,y + other._2)
	def unary_- = {new Int2DVec(-this.x,-this.y)}
	def *(other: Int2DVec) = this.x * other.x +  this.y * other.y
	def ~(other: Int2DVec) = new Int2DVec(this.x - other.x, this.y - other.y)
	override def toString() = "(" + this.x.toString + "," + this.y.toString + ")"
	/*
	def this() = { 
		this(0, 0)
		println("Creating Int2DVec(0,0)") 
	 }
	def this(prototype: Int2DVec) = { 
		this(prototype.x, prototype.y)
		println("Copy-constructor working") 
	}
	*/
}

object Int2DVec{
	def apply(x: Int, y:Int) = new Int2DVec(x,y)
	def apply()  = new Int2DVec(0,0)
	def apply(prototype: Int2DVec) = new Int2DVec(prototype.x,prototype.y)
	def unitVectorOf(vec : Int2DVec) = {
		val x = vec.x
		val y = vec.y
		val length : Double = (math.pow(x,2)+math.pow(y,2))
		(x/length,y/length) 
	}
}

object MainVec{
	def main(args : Array[String]) {
		val v1 = new Int2DVec(1,2)
		val v2 = new Int2DVec(10,20)
		val v3 = v1 + v2 
		val v4 = -v2
		val v5 = v1 * v2
		val v6 = v1 ~ v2
		println(v3)
		println(v4)
		println(v5)
		println(v6)
		println( Int2DVec())
		println( Int2DVec(v1+v1+v2))
		val v = new Int2DVec(20,30)
		val v_unit = Int2DVec.unitVectorOf(v)
		println(v + v_unit)
		println(v dodaj v_unit)
		val vp1 = new Int2DVec(2,2)
		val vp2 = new Int2DVec(-2,2)
		if(vp1 |-? vp2) println("Vectors vp1 and vp2 are ortagonal")
	}
}
//val A = new Int2DVec(2,3)
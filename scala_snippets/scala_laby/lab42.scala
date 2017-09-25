
object Appl42 {
	  def checkPredicate(pred: Boolean, predAsString: String, prefix: String = "Checking if ") {
		if (pred) println(prefix + predAsString + ": OK")
		else println(prefix + predAsString + ": Fail")
	  }
	  def sumSqrArrayIter(elems: Array[Int])={
		var sum = 0
		for (i <- elems) sum += i*i
		sum
	  }
	  def sumArrayIter(elems: Array[Int]) = {
		var sum = 0
		for (i <- elems) sum += i
		sum
	  }
  
	def sumSqrArrayRec1(i: Int, elems: Array[Int]): Int = {
		if (i < elems.size) elems(i)*elems(i) + sumSqrArrayRec1(i + 1, elems)
		else 0
	}
	def sumArrayRec1(i: Int, elems: Array[Int]): Int = {
		if (i < elems.size) elems(i) + sumArrayRec1(i + 1, elems)
		else 0
	}

	def sumSqrArrayRec2(elems: Array[Int]) = {
		val size = elems.size
		def goFrom(i: Int): Int = {
			if (i < size) elems(i)*elems(i) + goFrom(i+1)
			else 0
		}
		goFrom(0)
	}
	def sumArrayRec2(elems: Array[Int]) = {
		val size = elems.size
		def goFrom(i: Int): Int = {
			if (i < size) elems(i) + goFrom(i+1)
			else 0
		}
		goFrom(0)
	}
	import scala.annotation.tailrec
	def sumSqrArrayRec3(elems : Array[Int]){
		@tailrec def goFrom(i : Int,acc:Int,elems : Array[Int],size:Int) : Int={
			if (i==size) acc
			else  goFrom(i+1,acc +elems(i)*elems(i), elems,size)
		}
		goFrom(0,0,elems,elems.size)
	}
	  def main(args: Array[String]) {
		val a1To5 = (1 to 5).toArray
		println("Testing with a1To5 = " + a1To5.mkString("Array(", ", ", ") ..."))
		val expectResult = 15
		val expectResult1 = 55
		checkPredicate(sumArrayIter(a1To5) == expectResult, 
					   "sumArrayIter(0, a1To5) == " + expectResult)
		checkPredicate(sumArrayRec1(0, a1To5) == expectResult, 
					   "sumArrayRec1(0, a1To5) == " + expectResult)
		checkPredicate(sumArrayRec2(a1To5) == expectResult, 
					   "sumArrayRec2(a1To5) == " + expectResult)
		checkPredicate(sumSqrArrayIter(a1To5) == expectResult1, 
					   "sumArrayIter(0, a1To5) == " + expectResult1)
		val ble = sumSqrArrayRec1(0, a1To5)
		checkPredicate(ble == expectResult1, 
					   "sumArrayRec1(0, a1To5) == " + expectResult1 + ", " + ble)
		checkPredicate(sumSqrArrayRec2(a1To5) == expectResult1, 
					   "sumArrayRec2(a1To5) == " + expectResult1)
	  }
}
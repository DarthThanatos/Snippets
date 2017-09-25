class WDI(val n : Int, val A : List[Int]){
	def longest() = {
		var maks = 0
		var long_res = A(0) * A(1)
		var length = 2
		var res = A(0) * A(1)
		var q : Double = A(1)/A(0) 
		for (i <- 2 to (n-1)){
			if (A(i)/A(i-1) == q){
				res = res * A(i)
				length = length + 1
			}
			else {
				if (maks < length){
					maks = length
					long_res = res
				}
				q = A(i)/A(i-1)
				res = A(i)*A(i-1)
				length = 2
			}
		}
		long_res
	}
}

object Apple {
	def main(args : Array[String]) {
		val A = List(2,4,8,16,1,3,9)
		val B =  new WDI(6,A)
		println(B.longest())
	}
}
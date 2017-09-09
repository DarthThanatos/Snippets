
object Appl531 {
  def genSumArray(elems: Array[Int], f: (Int) => Int) = {
    var sum = 0
    for (e <- elems) sum += f(e)
    sum
  }
 
  def sum(elems: Array[Int]) = genSumArray(elems, (e: Int) => e)
 
  def main(args: Array[String]) {
    val a = Array(1,2,3,4,-5)
    println("sum(a) = " + sum(a))
  }
}
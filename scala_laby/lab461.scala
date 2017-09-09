object fibbCalc {
  private val cache = collection.mutable.Map[Long, Long]()
  def fibb(n: Long): Long = {
    assert(n >= 0)
    if (n == 0 || n == 1) n
    else cache.getOrElseUpdate(n, fibb(n - 2) + fibb(n - 1))
  }
}
 
object Appl461 {
  def fibb(n: Long): Long = {
    assert(n >= 0)
    if (n == 0 || n == 1) n
    else fibb(n - 2) + fibb(n - 1)
  }
 
  def main(args: Array[String]) {
    val start1 = System.nanoTime
    val fibb50 = fibb(50)
    val end1 = System.nanoTime
    println("fibb(50) = " + fibb50 + ", elapsed: " + (end1 - start1) / 1e6 + " ms")
     
    val start2 = System.nanoTime
    val augmFibb50 = fibbCalc.fibb(50)
    val end2 = System.nanoTime
    println("Augmented fibb(50) = " + augmFibb50 + ", elapsed: " + (end2 - start2) / 1e6 + " ms")
  }
}
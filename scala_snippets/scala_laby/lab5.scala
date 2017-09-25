
class NumOps {
  def succ(a: Int) = a + 1
  def pred(a: Int) = a - 1
  def maxFrom3 (d1: Double, d2: Double, d3: Double) : Double = math.max(math.max(d1,d2),d3)
}


def f(argByVal: Long, argByName: => Long) { 
  Thread.sleep(1000)
  println("In function: " + argByVal + ", " + argByName)
}

:paste
println("Before: " + System.currentTimeMillis)
f(System.currentTimeMillis(), System.currentTimeMillis())
println("After: " + System.currentTimeMillis)


def f(argByVal: Long, argByName => Long) { 
  Thread.sleep(1000)
  println("In function: " + argByVal + ", " + argByName)
}
:paste
println("Before: " + System.currentTimeMillis)
f(System.currentTimeMillis(), System.currentTimeMillis())
println("After: " + System.currentTimeMillis)
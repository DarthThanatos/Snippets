/*
package utils{
	object Passwd{
		def nextPasswd( passwdLen : Int) = {
			val passwd = new StringBuilder(passwdLen)
			val allowedChars = (('0' to '9') ++ ('A' to 'Z') ++ ('a' to 'z')).toArray ++ Array('$', '-', '.', '_')
			for (i <- 0 to passwdLen) passwd += allowedChars(util.Random.nextInt(allowedChars.length))
			passwd.toString
		}
		def nextChar(){}
	}
}
*/
/*
package p1 {
  class A1 { println("p1.A1") }
  package p2 {
    class A21 { println("p1.p2.A21")}
  }
}
*/
/*

package p1 {
  class C1 { 
    println("C1")
    def m1C1() { println("m1C1()") } 
  }
  package p2 {
    class C21 { println("C21") }
    class C22 { println("C22") }
    class C23 { println("C23") }
    object C2 {
      def m1OC2() { println("m1OC2") }
      def m2OC2(c1: C1) {
        import c1._
        println("m2OC2")
        m1C1()
      }
    }
  }
}*/
class C1 extends T1{
	def f(i : Int) = println(i)
}
sealed trait T1{
	def f(i : Int) : Unit
}
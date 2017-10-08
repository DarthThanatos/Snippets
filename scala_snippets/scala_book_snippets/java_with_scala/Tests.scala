package tests

object Tests{
	
	@MyIgnore
	def testData = List(0, 1, -1, 5, -5)

	def test1{
		assert(testData == (testData.head :: testData.tail))
	}

	def test2{
		assert(testData.contains(testData.head))
	}
}


object MainTest {

	def main(args: Array[String]){
		for {
			method <- Tests.getClass.getMethods
			if method.getName.startsWith("test")
			if method.getAnnotation(classOf[MyIgnore]) == null
		}{
			println("found a test method :" + method)
		}
	}
}
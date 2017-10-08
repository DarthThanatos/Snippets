object Tests{
	
	@Ignore
	def testData = List(0, 1, -1, 5, -5)

	def test1{
		assert(testData == (testData.head :: testData.tail))
	}

	def test2{
		assert(testData.contains(testData.head))
	}
}

for {
	method <- Tests.getClass.getMethods
	if method.getName.startsWith("test")
	if method.getAnnotation(classOf[Ignore]) == null
}{
	println("found a test method :" + method)
}
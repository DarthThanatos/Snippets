
import junit.framework.TestCase
import junit.framework.Assert.assertEquals
import junit.framework.Assert.fail
import element.Element.elem

//JUnit
class ElementTestCase extends TestCase {

    def testUniformElement() {
        val ele = elem('x', 2, 3)
        assertEquals(2, ele.width)
        assertEquals(3, ele.height)
        try {
            elem('x', -2, 3)
            fail()
        }
        catch {
           case e: IllegalArgumentException => // expected
        }
    }
}


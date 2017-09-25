
import org.testng.Assert.assertEquals

import org.testng.annotations.Test
import org.scalatest.testng.TestNGSuite
import element.Element.elem

//ScalaTest JUnit 4 (ran on TestNG) integration classes (wraper?)
class ElementJUnit4Tests {
    @Test def verifyUniformElement() {
        val ele = elem('x', 2, 3)
        assertEquals(ele.width, 2)
        assertEquals(ele.height, 3)
    }

    @Test(expectedExceptions = Array(classOf[IllegalArgumentException]))def elemShouldThrowIAE() { elem('x', -2, 3) }
}


//ScalaTest assertion syntax with trait TestNGSuite
class ElementTestNGTraitSuite extends TestNGSuite {
    @Test def verifyUniformElement() {
        val ele = elem('x', 2, 3)
        assert(ele.width === 2)
        expect(3) { ele.height }
        intercept[IllegalArgumentException] {
           elem('x', -2, 3)
        }
    }
}
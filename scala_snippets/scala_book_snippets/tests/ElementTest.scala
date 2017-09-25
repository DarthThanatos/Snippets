import org.scalatest.Suite 
import org.scalatest.FunSuite
import org.scalatest.junit.JUnit3Suite

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import element.Element.elem

//ScalaTest Suite
class ElementSuite extends Suite {
    def testUniformElement() {
        val ele = elem('x', 2, 3)
        assert(ele.width == 2)
    }
}

// ScalaTest subtype of Suite => FunSuite
class ElementFunSuite extends FunSuite {
    test("elem result should have passed width") {
        val ele = elem('x', 2, 3)
        assert(ele.width == 2)
    }
}


//ScalaTest Junit wrapper
class ElementJUnit3Suite extends JUnit3Suite {
    def testUniformElement() {
        val ele = elem('x', 2, 3)
        assert(ele.width === 2)
        expect(3) { ele.height }
        intercept[IllegalArgumentException] {
            elem('x', -2, 3)
        }
    }
}



//BDD - behaviour driven development, DSL - domain specific language

//Specifications in ScalaTest
class ElementFlatMatcherSpec extends FlatSpec with ShouldMatchers {
    "A UniformElement" should
        "have a width equal to the passed value" in {
    
        val ele = elem('x', 2, 3)
        ele.width should be (2)
    }
    it should "have a height equal to the passed value" in {
        val ele = elem('x', 2, 3)
        ele.height should be (3)
    }
    it should "throw an IAE if passed a negative width" in {
        evaluating {
            elem('x', -2, 3)
        } should produce [IllegalArgumentException]
    }
}



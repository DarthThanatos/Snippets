import org.scalatest.WordSpec
import org.scalatest.prop.Checkers
import org.scalacheck.Prop._
import element.Element.elem

//property based checking
// ScalaCheck
class ElementPropSpec extends WordSpec with Checkers {

    "elem result" must {
        "have passed width" in {
            check((w: Int) => w > 0 ==> (elem('x', w, 3).width == w))
        }
        "have passed height" in {
           check((h: Int) => h > 0 ==> (elem('x', 2, h).height == h))
        }
    }
	    
	
}

object ElementPropSpec{
	def main(args: Array[String]){
		val propSpec = new ElementPropSpec
	}

}
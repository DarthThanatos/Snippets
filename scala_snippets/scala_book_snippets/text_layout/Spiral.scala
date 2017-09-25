import element.Element.elem
import element.Element

object Spiral{
    
    val space = elem(" ")
    val corner = elem("+")
    
    val eastDirection = 0
    val southDirection = 1
    val westDirection = 2
    val northDirection = 3

    def nSides(args: Array[String]) = args(0).toInt

    def verticalBar(sp : Element) = elem('|', 1, sp.height)
    def horizontalBar(sp: Element) = elem('-', sp.width, 1)

    def currentSpiral(nEdges: Int, direction: Int) : Element = 
        spiral(nEdges - 1, (direction + 3) % 4)
        
    def returnSpiralWithNewLayer(sp: Element, direction: Int) : Element = 
        if(direction == eastDirection) (corner beside horizontalBar(sp)) above (sp beside space)
        else if(direction == southDirection) (sp above space) beside (corner above verticalBar(sp))
        else if(direction == westDirection) (space beside sp) above (horizontalBar(sp) beside corner)
        else (verticalBar(sp) above corner) beside (space above sp) 

    def spiral(nEdges: Int, direction: Int) : Element = 
        if (nEdges == 1) elem("+")
        else returnSpiralWithNewLayer(currentSpiral(nEdges, direction), direction)

    def main (args: Array[String]){
        println("spiral:\n" + spiral(nSides(args), eastDirection))
    }
}
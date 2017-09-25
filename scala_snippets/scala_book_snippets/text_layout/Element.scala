package element
import Element.elem 

abstract class Element{
    def contents: Array[String]

    def height = contents.length
    def width = if(height == 0) 0 else contents(0).length
    def above(that: Element) : Element = elem(widenedThis(that).contents ++ widenedThat(that).contents)
    def beside(that: Element) : Element = elem(mountThisContentsBeside(that))

    private def widenedThis(that: Element) = this widen that.width
    private def widenedThat(that: Element) = that widen this.width
    private def heightenedThis(that: Element) = this heighten that.height
    private def heightenedThat(that: Element) = that heighten this.height
    private def left(w : Int) = elem(' ', (w - width) / 2, height)
    private def right(w : Int) = elem(' ', w - width - left(w).width, height)
    private def top(h : Int) = elem(' ', width, (h - height) / 2)
    private def bottom(h: Int) = elem(' ', width, h - height - top(h).height)

    private def widen(w : Int) : Element = 
        if(w <= width) this else left(w) beside this beside right(w)

    private def heighten(h : Int) : Element =    
        if(h <= height) this else top(h) above this above bottom(h)

    override def toString = contents mkString "\n"

    private def mountThisContentsBeside(that: Element) : Array[String] = 
        (heightenedThis(that).contents zip heightenedThat(that).contents)
            .map{ case (this_el, that_el) => this_el + that_el }

    private def mountThisContentsBeside_ZipWith(that:Element) : Array[String] = 
        new Array[String](this.contents.length)
            .zipWithIndex
            .map{ case(_, i) => this.contents(i) + that.contents(i) }
}

object Element {

    private class ArrayElement(val contents: Array[String]) extends Element

    private class LineElement(s: String) extends Element{
        def contents = Array(s)
        override def height = 1 
        override def width = s.length
    }

    private class UniformElement(ch:Char, override val width: Int, override val height: Int) extends Element{
        if(width < 0 || height < 0) throw new IllegalArgumentException
        private val line = ch.toString * width
        def contents = Array.fill(height)(line)
    }

    def elem(contents : Array[String]) : Element = new ArrayElement(contents)
    def elem(s : String) : Element = new LineElement(s)
    def elem(ch : Char, width : Int, height : Int) : Element = new UniformElement(ch, width, height)
}

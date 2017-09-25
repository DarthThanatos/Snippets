package expr
import element.Element.elem
import element.Element

sealed abstract class Expr

case class Var(name: String) extends Expr
case class Number(num: Double) extends Expr
case class UnOp(operator: String, arg: Expr) extends Expr
case class BinOp(operator: String, left: Expr, right: Expr) extends Expr

class ExprFormatter{
    
    private val opGroups = 
        Array(
            Set("|", "||"),
            Set("&", "&&"),
            Set("^"),
            Set("==", "!="),
            Set("<", "<=", ">", ">="),
            Set("+", "-"),
            Set("*", "%")
        )

    private val assocs = 
        for(i <- 0 until opGroups.length; op <- opGroups(i)) yield op -> i

    private val precedence : Map[String, Int] = assocs.toMap
    private val unaryPrecedence = opGroups.length
    private val fractionPrecedence = -1
    
    private def stripDot(s: String) = 
        if (s endsWith ".0") s.substring(0, s.length - 2) else s

    private def top(left : Expr) = 
        format(left, fractionPrecedence)

    private def bot(right : Expr) = 
        format(right, fractionPrecedence)

    private def l(left: Expr, op: String) = 
        format(left, precedence(op))

    private def r(right: Expr, op: String) = 
        format(right, precedence(op))

    private def oper (op: String, left: Expr, right: Expr) = 
        l(left, op) beside elem(" " + op + " ") beside r(right, op)

    private def line(left: Expr, right: Expr) = 
        elem('-', top(left).width max bot(right).width, 1)

    private def frac(left: Expr, right: Expr) = 
        top(left) above line(left, right) above bot(right)

    private def onFracBinOp(left: Expr, right: Expr, enclPrec: Int) = 
        if(enclPrec != fractionPrecedence) frac(left, right) 
        else elem(" ") beside frac(left, right) beside elem(" ")

    private def onNotFracBinOp(op: String, left: Expr, right: Expr, enclPrec:Int) = 
        if(enclPrec <= precedence(op)) oper(op, left, right)
        else elem("(") beside oper(op, left, right) beside elem(")") 

    private def format(e: Expr, enclPrec: Int) : Element = e match {
        
        case Var(name) => elem(name)
        case Number(num) => elem(stripDot(num.toString))

        case UnOp(op, arg) => 
            elem(op) beside format(arg, unaryPrecedence)

        case BinOp("/", left, right) => 
            onFracBinOp(left, right, enclPrec)

        case BinOp(op, left, right) => 
            onNotFracBinOp(op, left, right, enclPrec)
    }   

    def format(e: Expr): Element = format(e, 0)
}

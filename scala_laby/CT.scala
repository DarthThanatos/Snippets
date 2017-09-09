sealed abstract class Expr
case class Number(s: Double) extends Expr
case class BinOp(operator: String, left : Expr, right : Expr) extends Expr
case class UnOp(operator : String, arg : Expr)  extends Expr

object ExprEval{
	def simplify(e : Expr) =  e match{
		case UnOp("+", Number(num)) => Number(num)
		case _ => e
	} 
	def evaluate(e: Expr) : Double = e match{
		case Number(num) => num
		case BinOp("+",left,right) => evaluate(left) + evaluate(right)
		case _ => println("unmatched expression"); 0
	}
}

object CTO extends App{
	import ExprEval._
	println(simplify(UnOp("+", Number(10))))
    println(evaluate(
					BinOp("+", 
						BinOp("+", Number(1.5), Number(5.5)), 
						Number(3)
						)
					)
			)
 }
import expr._

def simplifyTop(expr: Expr): Expr = expr match{
    case UnOp("-", UnOp("-", e)) => e // Double negation
    case BinOp("+", e, Number(0)) => e // Adding zero
    case BinOp("*", e, Number(1)) => e // Multiplying by one
    case _ => expr
}

def idMatcher(expr: Expr) = expr match {
    case BinOp(op, left, right) =>
        println(expr +" is a binary operation")
    case _ => 
}

def blankIdMatcher(expr: Expr) = expr match{
    case BinOp(_,_,_) => println(expr + " is a binary operation")
    case _ => println("It's sth else")
}

def describe(x: Any) = x match {
    case 5 => "five"
    case true => "truth"
    case "hello" => "hi!"
    case Nil => "the empty list"
    case _ => "something else"
}

println (describe(Nil))

def threeElemListMatcher(expr: Any) = expr match {
    case List(0, _, _) => println("found three elem list")
    case _ => 
}

threeElemListMatcher(List(0,1))  

def arbitraryListMatcher(expr: Any) = expr match {
    case List(0, _*) => println("found arbitrary list")
    case _ =>
}

arbitraryListMatcher(List(0,1))

def generalSize(x: Any) = x match {
    case s: String => s.length
    case m: Map[_, _] => m.size
    case _ => -1
}

println(generalSize("abc"))
println(generalSize(Map(1 -> 'a', 2 -> 'b')))
println(generalSize(math.Pi))

def variableBinding(expr: Expr) = expr match {
    case UnOp("abs", e @ UnOp("abs", _)) => e
    case _ =>
}

println(variableBinding(UnOp("abs", UnOp("abs", Number(312)))))

def simplifyAdd(e: Expr) = e match {
    case BinOp("+", x, y) if x == y =>
        BinOp("*", x, Number(2))
    case _ => e
}

println(simplifyAdd(BinOp("+", Number(1),Number(1))))

def describeLeaves(e: Expr): String = (e: @unchecked) match {
    case Number(_) => "a number"
    case Var(_) => "a variable"
}

val withDefault: Option[Int] => Int = {
    case Some(x) => x
    case None => 0
}

val second: PartialFunction[List[Int], Int] = {
    case x :: y :: _ => y
}

println(second(List(1,2)))

def show(e: Expr) = println((new ExprFormatter).format(e)+ "\n\n")

val e1 = BinOp("*", BinOp("/", Number(1), Number(2)), BinOp("+", Var("x"), Number(1)))
val e2 = BinOp("+", BinOp("/", Var("x"), Number(2)), BinOp("/", Number(1.5), Var("x")))
val e3 = BinOp("/", e1, e2)

for (e <- Array(e1, e2, e3)) show(e)

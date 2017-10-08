import scala.util.parsing.combinator._

object MyParsers extends RegexParsers {
    val ident: Parser[String] = """[a-zA-Z_]\w*""".r
}


class Arith extends JavaTokenParsers {
    def expr: Parser[Any] = term ~ rep("+" ~ term | "-" ~ term)
    def term: Parser[Any] = factor ~ rep("*" ~ factor | "/" ~ factor)
    def factor: Parser[Any] = floatingPointNumber | "(" ~ expr ~ ")"
}

object ParseExpr extends Arith {
    def main(args: Array[String]) {
        val input = "2 * (3 + 7)"
        println("input : "  + input)
        println(parseAll(expr, input))
    }
}
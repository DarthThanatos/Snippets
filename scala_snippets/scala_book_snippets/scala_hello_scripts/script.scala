//114 str or 100 str.
import java.io.PrintWriter
import java.io.File

println("Hello")

def withPrintWriter(file: File)(op: PrintWriter => Unit){
    val writer = new PrintWriter(file)
    try{
        op(writer)
    } finally{
        writer.close()
    }
}

def sum(a : Int, b : Int, c : Int) : Int = a + b + c
def carriedSum(a : Int)(b : Int, c : Int)(d : Int)(e : Int) = a + b + c + d + e

val file = new File("date.txt")
withPrintWriter(file){
    writer => writer.println(new java.util.Date) //prints current date, having toString method of a Date object
}

withPrintWriter(file){
    _.println(new java.util.Date) //same as above, but shorter
}

println(sum(1,2,3))
val a = carriedSum(1)_
println(a(3,4))
println(a(3,4)(5))
println(a(3,4)(5)(6))
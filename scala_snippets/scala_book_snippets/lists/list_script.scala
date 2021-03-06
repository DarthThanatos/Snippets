val fruit = "apples" :: ("oranges" :: ("pears" :: Nil))
val nums = 1 :: (2 :: (3 :: (4 :: Nil)))
val diag3 = 
    (1 :: (0 :: (0 :: Nil))) ::
    (0 :: (1 :: (0 :: Nil))) ::
    (0 :: (0 :: (1 :: Nil))) ::
    Nil
val empty = Nil

def append[T](xs: List[T], ys: List[T]): List[T] = xs match{
    case List() => ys
    case x :: xs1 => x :: append(xs1, ys)
}

println(fruit.map(_.toCharArray).flatten)
println(fruit.indices zip fruit)

def msort[T](less: (T, T) => Boolean)(xs: List[T]) : List[T]={
    def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match{
        case (Nil, _) => ys
        case (_, Nil) => xs
        case (x :: xs1, y::ys1) =>
            if (less(x,y)) x :: merge(xs1, ys)
            else y::merge(xs, ys1)
    }
    val n = xs.length / 2 
    if(n == 0) xs else {
        val (ys, zs) = xs splitAt n
        merge(msort(less)(ys), msort(less)(zs))
    }
}

println(msort((x: Int, y: Int) => x < y)(List(5,7,1,3)))
//span => dropWhile, takeWhile, 
//partition => filter(p), filter(not p)

def sum(xs: List[Int]): Int = (0 /: xs) (_ + _)
def product(xs: List[Int]): Int = (1 /: xs) (_ * _)
def reverseLeft[T](xs: List[T]) = (List[T]() /: xs)((ys, y) => y :: ys)

println(sum(List.range(1,6)))
println(product(List.range(1,6)))
println(reverseLeft(List(1,2,3)))

import scala.collection.mutable
val words = mutable.Set.empty[String]
val occurs = mutable.Map.empty[String, Int]

def getWordCount(word: String): Int = if (occurs.contains(word)) occurs(word) else 0
val splitted = "See Spot run. Run, Spot. Run!".split("[ !,.]+").map(_.toLowerCase)

splitted.foreach(words += _)
splitted.foreach(word => occurs += word -> (getWordCount(word) + 1))

val chooseBiggerLengthTuple : ((String, Int), (String,Int)) => (String, Int) = {
    case ((s1, i1), (s2, i2))  => if (s1.length >= s2.length) (s1,i1) else (s2,i2)
}

def arrIndexZipped (c: Array[String]) = (c zip c.indices)
val initialPair = ("",0)

def longestWord(c: Array[String]) = 
    (initialPair /: arrIndexZipped(c))(chooseBiggerLengthTuple)

def longestWordExpl(c: Array[String]) = 
    arrIndexZipped(c).foldLeft(initialPair)(chooseBiggerLengthTuple)

println(words)
println(occurs)
println(longestWord("The quick brown fox".split(" ")))
println(longestWordExpl("The quick brown fox".split(" ")))

class Person(val firstName: String, val lastName: String) extends Ordered[Person]{
    
    def compare(that: Person) = {
        val lastNameComparison = lastName.compareToIgnoreCase(that.lastName)
        if(lastNameComparison != 0) lastNameComparison else firstName.compareToIgnoreCase(that.firstName)
    }
    
    override def toString = firstName + " " + lastName
}

def maxListImplParm[T](elements: List[T])(implicit orderer: T => Ordered[T]): T = 
    elements match {
        case List() => throw new IllegalArgumentException("empty list") 
        case List(x) => x 
        case x :: rest => 
            val maxRest = maxListImplParm(rest)(orderer)
            if(orderer(x) > maxRest) x else maxRest
    }

val people = 
    List(
        new Person ("Larry", "Wall"),
        new Person("Walter", "White"),
        new Person("Gus", "Fring"),
        new Person("Jessie", "Pinkman"),
        new Person("Alan", "Turing")
    )

println(maxListImplParm(people))
println(maxListImplParm(List("one", "two", "three")))


def maxListImplParm2[T](elements: List[T])(implicit orderer: T => Ordered[T]): T = 
    elements match {
        case List() => throw new IllegalArgumentException("empty list") 
        case List(x) => x 
        case x :: rest => 
            val maxRest = maxListImplParm(rest)
            if(x > maxRest) x else maxRest
    }

println(maxListImplParm2(people))
println(maxListImplParm2(List("one", "two", "three")))

val l = List(("A", 1, 4), ("A", 2, 5), ("A", 3, 6), ("B", 1, 4), ("B", 2, 5), ("B", 3, 6))

import scala.language.postfixOps
def unzippedGroup(k : String, v: List[(String, Int, Int)]) = 
    v map {case (k, v1, v2) => (v1, v2)} unzip

val groupedL = 
    l groupBy (_._1) map {
        case (k, v) => (k, unzippedGroup(k,v))      
    }

println(groupedL)
println(l groupBy (_._1))


println(people.isDefinedAt(30))

import scala.reflect.ClassTag

def evenElements[T: ClassTag](xs: Vector[T]): Array[T] = 
    new Array[T]((xs.length + 1)/2).zipWithIndex.map{ case (_, index) => xs(2 * index) }

def printEvenRes[T: ClassTag](xs: Vector[T]){
    println (evenElements(xs) mkString("Array(",",",")"))
}

printEvenRes(Vector.range(1, 5))
printEvenRes(Vector(1 to 10: _*))
printEvenRes(Vector("this", "is", "a", "test", "run"))

println((Vector(1 to 10:_*).view map(_ + 1) map(_ * 2)) force)

def negate(xs: collection.mutable.Seq[Int]) = 
    for (i <- 0 until xs.length) xs(i) = -xs(i)

val arr = (0 to 9).toArray
println(negate(arr.view.slice(3,6)))
println(arr mkString("Array(",",",")"))
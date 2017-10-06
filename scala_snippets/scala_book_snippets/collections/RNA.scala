import collection.IndexedSeqLike
import collection.mutable.{Builder, ArrayBuffer}
import collection.generic.CanBuildFrom

abstract class Base 
case object A extends Base
case object T extends Base
case object G extends Base
case object U extends Base

object Base {
	val fromInt: Int => Base = Array(A,T,G,U)
	val toInt: Base => Int = Map(A->0, T->1, G->2, U->3)
} 


final class RNA private(val groups: Array[Int], val length: Int)
  extends IndexedSeq[Base] with IndexedSeqLike[Base, RNA]{
	import RNA._
	
	override protected[this] def newBuilder: Builder[Base, RNA] = 
		RNA.newBuilder

	def apply(idx: Int): Base = {
		if(idx < 0 || length <= idx) throw new IndexOutOfBoundsException
		Base.fromInt(groups(idx / N) >> ( (idx % N) * S) & M)
	}	

	override def foreach[U](f: Base => U): Unit = {
		var i = 0
		var b = 0
		while (i < length){
			b = if(i % N == 0) groups(i / N) else b >>> S
			f(Base.fromInt(b & M))
			i += 1
		}
	}
}

object RNA{
	
	private val S = 2 // Number of bits necessary to represent group (4 case classes, 2 bits necessary, lg(CC), CC - case classes amount)
	private val N = 32 / S // Number of groups that fit in an Int(32 bits)
	private val M = (1 << S) - 1 // Bitmask to isolate a group

	def fromSeq(buf: Seq[Base]) : RNA = {
		val groups = new Array[Int]((buf.length + N - 1) / N)
		for(i<- 0 until buf.length) groups(i / N) |= Base.toInt(buf(i)) << ((i % N) * S)
		new RNA(groups, buf.length)	
	}

	def apply(bases: Base*) = fromSeq(bases)

	def newBuilder: Builder[Base, RNA] = 
		new ArrayBuffer[Base] mapResult fromSeq

	implicit def canBuildFrom: CanBuildFrom [RNA, Base, RNA] = 
		new CanBuildFrom[RNA, Base, RNA]{
			def apply(): Builder[Base, RNA] = newBuilder
			def apply(from: RNA): Builder[Base, RNA] = newBuilder
		}

}

val xs = List(A, G,T,A)

println(xs)
println(RNA.fromSeq(xs))

val rna = RNA(A,U,G,G,T)
println("Length: " + rna.length)
println(rna.last)
println(rna.take(3))
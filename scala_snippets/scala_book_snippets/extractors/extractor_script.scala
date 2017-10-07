object Email extends ((String, String) => String){ //or extends Function2[String, String, String]
	
	def apply(user: String, domain: String) = user + "@" + domain

	def unapply(str: String): Option[(String, String)] = {
		val parts = str split "@"
		if(parts.length == 2) Some(parts(0), parts(1)) else None
	}
}

object Domain{
	def apply(parts: String*): String = 
		parts.reverse.mkString(".")

	def unapplySeq(whole: String): Option[Seq[String]] = 
		Some(whole.split("\\.").reverse)
}

def isInDotCom(s: String): Boolean = s match {
	case Email(_, Domain("com", _*)) => true
	case _ => false
}

println(isInDotCom("rob@gmail.com"))
println(isInDotCom("rob@gmail.notcom"))

object ExpandedEmail{
	def unapplySeq(email: String): Option[(String, Seq[String])] = {
		val parts = email split "@"
		if(parts.length == 2) Some(parts(0), parts(1).split("\\.").reverse) 
		else None
	}
}

val s = "rob@support.gmail.com"
val ExpandedEmail(name, topDom, subDoms @ _*) = s 
println(name + " " + topDom + " " + subDoms)

object Twice{

	def apply(s : String): String  = s + s
	
	def unapply(s: String) : Option[String] = {
		val length = s.length / 2
		val half = s.substring(0, length)
		if(half == s.substring(length)) Some(half) else None
	}

}

object UpperCase{
	def unapply(s: String): Boolean = s.toUpperCase == s
}

def userTwiceUpper(s: String) = s match{

	case Email(y @ Twice(x @ UpperCase()), domain) =>
		"match: " + x + " in domain: " + domain +" with twice: " + y


	case Email(y @ Twice(x), domain) =>
		"match not UpperCase: " + x + " in domain: " + domain +" with twice: " + y

	case _ => "no match"
}

println(userTwiceUpper("DIDI@hotmail.com"))
println(userTwiceUpper("DIDO@hotmail.com"))
println(userTwiceUpper("didi@hotmail.com"))
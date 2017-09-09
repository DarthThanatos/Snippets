(*
 * Zadanie domowe 1, czesc 1
 *  structure file
 *)
structure id276324 :> PART_ONE =
struct
  exception NotImplemented

  datatype 'a tree= Leaf of 'a | Node of 'a tree * 'a * 'a tree

  fun sum (n:int) = 
    if (n=1) then 1
    else n + sum(n-1);

  fun fac (n:int) = 
    if n=1 orelse n=0 then 1
    else n * fac(n-1);

  fun fib (n:int) = 
    if n=0 orelse n=1 then 1
    else fib(n-1) + fib(n-2);
     
  fun gcd (a:int , b:int) = 
    if a=0 then b
    else if b=0 then a
    else if a>b then gcd(a mod b,b)
    else gcd(a,b mod a);
      
  fun max (l:int list) =
   case l of
      nil => 0
    | head :: tail =>
        if tail = [] orelse head > max tail
        then head
        else max tail;

  fun sumTree (t:int tree) = 
    case t of
      Leaf l => l
    | Node (l,v,r) => v + sumTree l + sumTree r;

  fun depth (t:'a tree) = 
    case t of
      Leaf l => 0
    | Node (l, _, p) =>
        1 + (fn (x,y) => if x>y then x else y)(depth l,depth p);

(*   aplikacja funkcji bez nazwy (fn)  *)

fun binSearch (t:int tree) (x:int) =
    case t of
      Leaf l => l=x
    | Node (l,v,r) => 
        if x=v then true
        else if x<v then binSearch l x
        else binSearch r x;
		
  fun preorder (t:'a tree) =
    case t of
      Leaf l => [l]
    | Node(l,v,r) => [v] @ preorder l @ preorder r;

  fun listAdd [] (b:int list) = b
    | listAdd (a:int list) [] = a
    | listAdd (a:int list as ha :: ta) (b:int list as hb :: tb) =
        (ha + hb) :: listAdd ta tb;
		
 fun insert (m:int) [] = [m]
    | insert (m:int) (l:int list as h::t) =
        if m < h then m :: l
        else h :: insert m t;

  fun insort (l:int list) =
    let
      fun sort [] res = res
        | sort (h::t) res = sort t (insert h res)
    in
      sort l nil
		end;
	  
 fun compose f g  = (fn x=> g (f x))
  fun curry f x y  =  f(x,y)
  fun uncurry f (x,y) = f x y
  fun multifun f n = if n = 1 then (fn x => f x)
                        else (fn x =>f ( (multifun f (n-1)) x ))
 
  fun ltake _ 0 = []
        |ltake [] _ = []
        |ltake (x::xs) n = x::(ltake xs (n-1))
  fun lall _ [] = true
        |lall f (x::xs)= if (f x) then (lall f xs) else false
  fun lmap _ [] = []
        |lmap f (x::xs)= (f x)::(lmap f xs)
  fun lrev [] = []
        |lrev (x::xs) = (lrev xs) @ [x]
  fun lzip ([],_) = []
        |lzip (_,[]) = []
        |lzip ((x::xs),(y::ys)) = (x,y)::(lzip (xs,ys))
  fun split [] = ([],[])
        |split [x] = ([x],[])
        |split (x1::x2::xs) = 
			let 
				val (x1s,x2s) = split xs
            in 
				((x1::x1s),(x2::x2s)) 
			end
			
  fun cartprod _ [] =[]
        |cartprod [] _ = []
        |cartprod (x::xs) ys = (lmap (fn y=> (x,y)) ys) @ (cartprod xs ys)

end

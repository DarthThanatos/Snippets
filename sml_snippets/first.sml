
val x = 17

fun sil ()=
	let
		val x =  1
	in 
		(let val x = 22 in x + 1 end ) + (let val y = x + 1 in y + 5 end)
	end
	
val y = 2	
val z = (x+y)+(y+2);
val q =z+1;
val abs_of_z = if z<0 then 0-z else z;
val abs_of_z_simpler = abs z;
fun pow(x: int, y : int) =
	if y=0
	then 1
	else x * pow(x,y-1)
	
fun cube(x : int) =
	pow(x,3)
	
val sf = cube(4)

val ft = pow (2,pow(2,2)) + pow(4,2) + cube(2) + 2

fun silnia (x : int)=
	if x=0 then 1
	else x*silnia(x-1)
	
fun swap ( pr : int*bool )=
	(#2 pr, #1 pr)

fun sum_two_pairs (pr1: int*int, pr2 : int *int ) =
	(#1 pr1) + (#2 pr1) + (#1 pr2) + (#2 pr2)
	
fun div_mod (x: int, y : int)=
	(x div y, x mod y)
	
fun sort_pair(pr : int*int)=
	if #1 pr < #2 pr then pr
	else (#2 pr, #1 pr)
	
fun sum_list (xs: int list) =
	if null xs
	then 0
	else hd xs + sum_list(tl xs)
	
fun append (l1 : 'a list, l2 : 'a list) =
	if null l1
	then l2
	else hd l1 :: ( append (tl(l1),l2) )
		
fun one_sum (pr:int*int) =
	#1 pr + #2 pr

fun sum_pairL (PL : (int*int) list) =
	if null PL then 0 
	else one_sum(hd PL) + sum_pairL(tl PL)  
	
fun firsts (l1 : (int*int) list) =
	if null l1 then []
	else #1 (hd l1) :: firsts (tl l1)
	
fun max (l : int list) =
	if null l then 0
	else let val maks = max(tl l) in
		if hd l > maks then hd l
		else maks
		end
fun sumEl ( l1 : int list, l2 : int list) =
	if null l1 andalso null l2 then []
	else 
		if null l1 then (hd l2) :: sumEl(l1,tl l2)
		else
			if null l2 then (hd l1) :: sumEl(tl l1, l2)
			else 
				(hd(l1) + hd(l2)) :: sumEl(tl l1, tl l2)
	
fun insert (w: int, l : int list) =
	if null l then w::l
	else
		if w<=hd l then w:: l 
		else hd l ::insert (w,tl l)
	
fun sort (l: int list) =
	if null l then []
	else insert(hd l,sort (tl l))


fun lrev ( l : int list )=
	
	let
		fun help(l : int list, le : int list) =
			if null l 
			then le
			else  append(help(tl l, le), (hd l::[]) )
	in
		if null l
		then []
		else help(l, [])
	end
	
fun isempty(l : int list) =
	if not (null l) then false
	else true

val x = 20
val x = 21 

datatype mytype = TwoInts of int*int
				| Str of string
				| Pizza
				| Int of int
				| Ble
val a = Str "hi"
val b = Str
val c = Pizza
val d = TwoInts(1,2)
val e = a
val f = Int (2)

fun f x =
	case x of 
		Pizza => 3
		|Str S =>8
		|TwoInts(i1 ,i2) => i1+i2
		|Int i => 1
		|Ble => 2
		
datatype exp = 
	Constant of int
	| Negate of exp
	| Add of exp*exp
	| Multiply of exp*exp
	
fun eval e =
	case e of
		Constant i => i
		| Negate e1 => ~(eval e1)
		| Add (e1,e2) => (eval e1) + (eval e2)
		| Multiply (e1,e2) => (eval e1) * (eval e2)
		
fun numberOfAdds e =
	case e of
		Constant i => 0 
		|Negate e2 => numberOfAdds e2
		|Add (e1,e2) => 1 + numberOfAdds e1 + numberOfAdds e2
		|Multiply (e1,e2) => numberOfAdds e1 + numberOfAdds e2
		
val example_exp = Add (Constant 19, Negate (Constant 4))
val example_ans = eval example_exp
	
val example_add = numberOfAdds(Multiply((example_exp,Multiply(example_exp,example_exp))))	

datatype 'a tree =
	Leaf of 'a
	| Node of 'a tree * 'a * 'a tree

fun sumTree (t : int tree)=
	case t of
		Leaf i => i
		| Node (l,w,p) => sumTree l + w + sumTree p

fun bigger (v1 : int, v2 : int) =
	if v1>=v2 then v1
	else v2
	
fun depth (t : 'a tree) = 
	case t of
		Leaf i => 0
		| Node (l,w,p) => 1 + bigger (depth l, depth p)
		
fun binSearch (t: int tree, s : int )= 
	case t of 
		Leaf i => if i = s then true else false
		| Node (l,w,p) => if w = s then true 
			else if (s<w) then binSearch(l,s)
				else binSearch(p,s)

datatype lista =
	Lista of int list 

fun preorder (T: 'a tree)=
	let
		fun help (t: 'a tree, L: 'a list) =
			case t of 
				Leaf i => i :: []
				| Node (l,w,p) => w :: append((help(l, L)),(help(p,L))) 
	in
		help(T,[])
	end

fun ltake (L : 'a list, rank : int) =
		let 
			fun help (l : 'a list, aktualnaPozycja : int ) =
				if aktualnaPozycja = rank then (hd l) :: []
				else
					if null l then []
					else (hd l) :: help(tl l, aktualnaPozycja + 1) 
		in 
			help (L,1)
		end

fun lzip (L1 : 'a list, L2 : 'b list) =
	if null L1 orelse null L2 then []
	else (hd L1, hd L2) :: lzip(tl L1, tl L2)

val para = ([1],[2])

fun proba (zm : int list ) =
	(hd zm :: #1 para, #2 para)

fun split(L : 'a list) =
	let 
		fun help (L : 'a list, ind : int)=
			if null L then ([],[])
			else 
				if  ind mod 2 = 0 then( hd L :: #1 (help(tl L, ind + 1)),( #2 (help (tl L, ind+1))))
				else ( #1 (help(tl L, ind + 1)),( hd L :: #2 (help (tl L, ind+1))))
	in
		help(L,0)
	end

fun split1 ( L: ('a list *'a list) )= 
		 (#1 L, #2 L)
		
fun cartprod (P : 'a list, Q : 'b list)=
	let 
		fun help (p : 'a list, q : 'b list)=
			if null (tl q) then 
				if null (tl p) then (hd p, hd q)  :: []
				else  (hd p, hd q) :: help (tl p, Q)
			else (hd p, hd q) :: help ( p, tl q )	
	in 
		help (P,Q)
	end

fun N (f,n,x) =
	if n =0 then x
	else f (N(f,n-1,x))
	
fun lall (f, L : 'a list ) = 
	if null L then true
	else 
		if f (hd L) then lall (f,tl L) else false

fun lmap (f, L : 'a list ) =
	if null L then []
	else f (hd L) :: lmap (f, tl L)
	
fun multifun (f, n : int, x: int)=
	if n = 1 then f x
	else f (multifun (f,n-1,x))
	
 fun policz ( koniec: int) = 
	let 
		fun lista (x :int)=
			if x = koniec then x::[]
			else x::lista(x+1)
	in
		lista (~1)
	end
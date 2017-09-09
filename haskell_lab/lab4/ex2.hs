-- product type example (one constructor)
data CartInt2DVec = MkCartInt2DVec Int Int -- konwencja: prefix 'Mk' dla konstruktora

type X = Int 
type Y = Int 

xCoord :: CartInt2DVec -> Int
xCoord (MkCartInt2DVec x _) = x

yCoord :: CartInt2DVec -> Int
yCoord (MkCartInt2DVec _ y) = y
------------------------------------------------
data Cart2DVec' a = MkCart2DVec' a a

xCoord' :: Cart2DVec' a -> a
xCoord' (MkCart2DVec' x _) = x

yCoord' :: Cart2DVec' a -> a
yCoord' (MkCart2DVec' _ y) = y
-------------------------------------------------
data Cart2DVec'' a = MkCart2DVec'' {x::a, y::a}

xCoord'' :: Cart2DVec'' a -> a
xCoord'' (MkCart2DVec'' {x = xVal, y = _}) = xVal

yCoord'' :: Cart2DVec'' a -> a
yCoord'' (MkCart2DVec'' {y = yVal, x = _}) = yVal

---------------------------------------------------
data Vector a = Vector a a a deriving (Show)
vplus :: (Num t) => Vector t -> Vector t -> Vector t
-- vplus (Vector i j k) (Vector x y z) = Vector (i + x) (j + y) (k + z) 
(Vector i j k) `vplus` (Vector x y z) = Vector (i + x) (j + y) (k + z) 
vectMult :: ( Num t) => Vector t -> t -> Vector t
( Vector i j k ) `vectMult` m = Vector ( i*m) (j* m) ( k*m) 
scalarMult :: (Num t) => Vector t -> Vector t -> t
( Vector i j k ) `scalarMult` ( Vector l m n) = i*l + j*m + k *n

-------------------------------------------------------
-- sum type example (two constructors)
--infixr 5 :-
data List a = EmptyL | a :- (List a) deriving Show

--infixr 5 .++
(.++) :: List a -> List a -> List a
EmptyL .++ ys = ys
(x :- xs) .++ ys = x :- (xs .++ ys)

head' :: List a -> a
head' EmptyL      = error "head': the empty list has no head!"
head' (x :- xs) = x
-------------------------------------------------------
-- enum type example (special case of sum type)
data ThreeColors = Blue |
                   White |
                   Red

type ActorName = String

leadingActor :: ThreeColors -> ActorName
leadingActor Blue  = "Juliette Binoche"
leadingActor White = "Zbigniew Zamachowski"
leadingActor Red   = "Irene Jacob"

----------------------------------------------------------
data Cart3DVec a = Cart3DVec a a a 
xCoord3D :: (Num a) => Cart3DVec a -> a
xCoord3D (Cart3DVec x _ _) = x

data Cart3DVec' a = Cart3DVec' {x'::a, y'::a, z'::a}
-----------------------------------------------------------
data Shape = Circle Float |
             Rectangle Float Float

area :: Shape -> Float
area (Circle r) = pi * (r ** 2)
area (Rectangle width height) = width * height
-----------------------------------------------------------
data Tree a = EmptyT |
              Node a (Tree a) (Tree a)
              deriving Show


rootValue :: Tree a -> a
rootValue EmptyT = error "Error"
rootValue (Node root subtreeOne subtreeTwo) = root
-----------------------------------------------------------

data TrafficLights = GreenLight | YellowLight | RedLight

actionFor :: TrafficLights -> String
actionFor GreenLight = "Go and blow me"
actionFor YellowLight = "Slowly stop and stop blowing"
actionFor RedLight = "Break time"
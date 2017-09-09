{-
-- it works, but 'name' collides with what is below
type Name = String
type Surname = String
type Age = Int

data Person = MkPerson {name :: Name, surname :: Surname, age :: Age}
data Person' = Person' {name' :: String, surname' :: String, age' :: Int}
data Person'' = Person'' String String Int

name'' :: Person'' -> String 
name'' (Person'' n _ _) = n
-}

-------------------------------------------------------------------------
{-
newtype Foo a = MkFoo {value :: a, name :: String}
instance Show (Foo a) where
    show Foo{ v = value, n = name } = "Name: " ++ n ++ " with " ++ show v
-}

data Foo a = MkFoo { value :: a, name :: String }
instance (Show a) => Show (Foo a) where
    show MkFoo{ value = v, name = n } = "Name: " ++ n ++ " with " ++ show v


data Tree a = Node [Tree a] a | Leaf a deriving Show
maxValue :: Ord a => Tree a -> a
maxValue (Leaf v) = v
maxValue (Node t v) = foldl1 max (v:(map maxValue t))
tree = Node [Node [Leaf 0, Leaf 2, Leaf 1] 20, Leaf 3, Node [Leaf 15] 10] 100

--------------------------------------------------------------------------
data MyInt = MkMyInt Int
instance Ord MyInt where 
    (<=) (MkMyInt i1) (MkMyInt i2) = i1 <= i2

instance Eq MyInt where 
    (==) (MkMyInt i1) (MkMyInt i2) = i1 == i2

{-
1. Podać definicję abstrakcyjnego typu danych
2. Napisać instancję Eq dla
data Pair a b = MkPair a b
3. Jest definicja drzewa:
data BinTree a = EmptyBT | NodeBT a BinTree a BinTree a
Napisać 
flattenBTInOrder :: BinTree a -> [a] 
(spłaszczenie drzewa do listy, w kolejności in-order)
-}
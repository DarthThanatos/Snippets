data BinIntTree = EmptyIntBT |
                   IntNodeBT Int BinIntTree BinIntTree deriving Show

sumBinIntTree :: BinIntTree -> Int 
sumBinIntTree EmptyIntBT = 0
sumBinIntTree (IntNodeBT val left right) = val + (sumBinIntTree left) + (sumBinIntTree right)
---------------------------------------------------------------
data BinTree a = EmptyBT |
                 NodeBT a (BinTree a) (BinTree a) deriving Show

sumBinTree :: (Num a) => BinTree a -> a
sumBinTree EmptyBT = 0
sumBinTree (NodeBT n lt rt) = n + sumBinTree lt + sumBinTree rt
---------------------------------------------------------------
data Expr a = Lit a | -- literal/value a, e.g. Lit 2 = 2
              Add (Expr a) (Expr a) |
              Sub (Expr a) (Expr a) |
              Mul (Expr a) (Expr a)
 

eval :: Num a => Expr a -> a
eval (Lit n) = n
eval (Add e1 e2) = eval e1 + eval e2
eval (Sub e1 e2) = eval e1 - eval e2
eval (Mul e1 e2) = eval e1 * eval e2

show' :: Show a => Expr a -> String
show' (Lit n) = show n
show' (Add e1 e2) = "(" ++ show' e1 ++ "+" ++ show' e2 ++ ")"
---------------------------------------------------------------
depthOfBT :: BinTree a -> Int -- głębokość drzewa binarnego
depthOfBT EmptyBT = 0
depthOfBT (NodeBT _ left right) = 1 + max (depthOfBT left) (depthOfBT right)
--(NodeBT "A" (NodeBT "B" (NodeBT "D" EmptyBT EmptyBT) (NodeBT "E" (NodeBT "H" EmptyBT EmptyBT) (NodeBT "I" EmptyBT EmptyBT))) (NodeBT "C" (NodeBT "F" EmptyBT EmptyBT) (NodeBT "G" EmptyBT EmptyBT)))
--(NodeBT 10 (NodeBT 5 (NodeBT 4 EmptyBT EmptyBT) (NodeBT 6 (NodeBT 5 EmptyBT EmptyBT) (NodeBT 7 EmptyBT EmptyBT))) (NodeBT (-1) (NodeBT (-10) EmptyBT EmptyBT) (NodeBT 3 EmptyBT EmptyBT)))
flattenBTPost :: BinTree a -> [a]  -- napisać trzy wersje: preorder, inorder, postorder
flattenBTPost EmptyBT = []
flattenBTPost (NodeBT val left right) = flattenBTPost left ++ flattenBTPost right ++ [val]
flattenBTPre :: BinTree a -> [a]  -- napisać trzy wersje: preorder, inorder, postorder
flattenBTPre EmptyBT = []
flattenBTPre (NodeBT val left right) = val : (flattenBTPre left ++ flattenBTPre right)
flattenBTIn :: BinTree a -> [a]  -- napisać trzy wersje: preorder, inorder, postorder
flattenBTIn EmptyBT = []
flattenBTIn (NodeBT val left right) = flattenBTIn left ++ [val] ++ flattenBTIn right


mapBT :: (a -> b) -> BinTree a -> BinTree b -- funkcja map dla drzewa binarnego
mapBT _ EmptyBT = EmptyBT
mapBT f (NodeBT val left right) = NodeBT (f val) (mapBT f left) (mapBT f right)

insert :: Ord a => a -> BinTree a -> BinTree a -- insert element into BinTree
insert val EmptyBT = NodeBT val EmptyBT EmptyBT
insert val (NodeBT cmp left right) = if val < cmp then NodeBT cmp (insert val left) right else NodeBT cmp left (insert val right)


list2BST :: Ord a => [a] -> BinTree a -- list to Binary Search Tree (BST)
list2BST xs = list2BSTHlp xs EmptyBT
    where 
        list2BSTHlp [] tree = tree
        list2BSTHlp (x:xs) tree = list2BSTHlp xs $ insert x tree

sortBT :: Ord a => BinTree a -> BinTree a 
sortBT tree = list2BST (flattenBTIn tree)

occurs :: Eq a => a -> BinTree a -> Int -- liczba wystąpień elementu w drzewie binarnym
occurs _ (EmptyBT) = 0
occurs searched (NodeBT val left right) = if searched == val 
    then 1 + (occurs searched left) + (occurs searched right) 
    else (occurs searched left) + (occurs searched right)

elemOf :: Eq a => a -> BinTree a -> Bool -- sprawdzenie, czy element znajduje się w drzewie
elemOf _ EmptyBT = False
elemOf searched (NodeBT val left right) = if val == searched 
    then True 
    else (elemOf searched left) || (elemOf searched right)



minElemOf :: Ord a => BinTree a -> a
minElemOf EmptyBT = error "Empty Tree"
minElemOf (NodeBT val EmptyBT right) = foldl1 min ((minElemOf right) : [val])
minElemOf (NodeBT val left EmptyBT) = foldl1 min ((minElemOf left) : [val])
minElemOf (NodeBT val left right) = foldl1 min ((minElemOf left) : (minElemOf right) : [val])

{-
maxElemOf :: Ord a => BinTree a -> a
maxElemOf (EmptyBT) = 0
maxElemOf (NodeBT val left right) = foldl1 max ((maxElemOf left) : (maxElemOf right) : [val])
-}
{-
foldBinTree :: (a -> b -> b -> b) -> b -> BinTree a -> b -- fold dla drzewa binarnego
reflect :: BinTree a -> BinTree a -- 'odbicie lustrzane' drzewa binarnego
-}
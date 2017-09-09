import Control.Applicative

fun = do
    putStrLn "Podaj imie: "
    s <- getLine
    putStrLn $ "Witaj " ++ s

fun_refactored = 
    putStrLn "Podaj imie: " >>
    getLine >>=
    \x -> return ("Witaj " ++ x) >>=
    putStrLn

foldZip = foldr (+) 0 (ZipList [12,3])
zip_expr = foldr (+) 0 ((*) <$> ZipList[1,2,3] <*> ZipList[4,5,6])
--wrong_expr = foldr (+) 0 ((*)<$>ZipList[1,2,3]<*>((+1)ZipList[4,5,6]))
corrected_expr = foldr (+) 0 ((*) <$> ZipList[1,2,3] <*> ((+1) <$> ZipList[4,5,6]))

data Tree a = Node a (Tree a) (Tree a) | Leaf deriving Show
instance Functor Tree where
    fmap f Leaf = Leaf
    fmap f (Node x left right) = Node (f x) (fmap f left) (fmap f right)

instance Applicative Tree where
    pure x = Node x Leaf Leaf
    sth <*> Leaf = Leaf
    Leaf <*> sth = Leaf
    (Node f leftF rightF) <*> (Node x leftX rightX) = 
        Node (f x) (leftF <*> leftX) (rightF <*> rightX)

t = Node 2 -- 3
    (Node 1  -- 2
    (Node 2 -- 5
    (pure 8 ::Tree Int) -- 11
    (pure 9 :: Tree Int))  --10
    (Node 3 -- 0
    (pure 7 :: Tree Int) -- 1
    (pure 4 :: Tree Int)))  -- 8
    (pure 1 :: Tree Int) -- 2

ft = Node (\x -> x + 1) 
    (Node (*2) 
    (Node (+3) 
    (pure (+3) ::Tree (Int -> Int)) 
    (pure (+1) :: Tree (Int -> Int))) 
    (Node (\x -> 0) 
    (pure (\x -> 1) :: Tree (Int -> Int)) 
    (pure (+4) :: Tree (Int -> Int)))) 
    (pure (+1) :: Tree (Int -> Int))


-- data Fun (a->b) = Fun a
-- data Fun a b = Fun (a -> b)

{-
    1. Podać definicję funktora, po co i kilka przykladow
    2. podać wyniki:
        a) fmap (*2) (Left 3)
        b) odd <$> [1..4]
        c) pure (>) <*> Just 4 <*> Just 2
        d) pure (\x y z -> [x,y,z]) <*> Right 1 <*> Right 2 <*> Left 1
    3. Przepisać używając do:
        a) putStr "Computer " >> putStrLn "Science"
        b) getLine >>= \l -> putStrLn l >> putStrLn l
        c) getLine >>= \l -> if length l > 10 then putStrLn "Too long" else putStrLn l 

    -- na kartkowke: 
    co to jest monada
    wartosciowanie wyrazenia monadycznego

-}

a_expr = do 
    putStr "Computer "
    putStrLn "Science"

b_expr = do
    l <- getLine 
    putStrLn l 
    putStrLn l

c_expr = do
    l <- getLine
    let res = if length l > 10 then "Too long" else l 
    putStrLn res

c_expr' = do 
    l <- getLine
    if length l > 10 then putStrLn "Too long" else putStrLn l 

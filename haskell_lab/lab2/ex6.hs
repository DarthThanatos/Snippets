{-# LANGUAGE BangPatterns #-}

fib :: (Num a, Eq a) => a -> a
fib n =
 if n == 0 || n == 1 then n
 else fib (n - 2) + fib (n - 1)

fibs = 0 : 1 : zipWith (+) fibs (tail fibs) :: [Int]

fib2 :: Int -> Int
fib2 n = tellFib n fibs
            where 
                tellFib :: Int -> [Int] -> Int
                tellFib x _ | x < 0 = -1
                tellFib x xs | x == 0 = head xs 
                tellFib x xs = tellFib (x-1) (tail xs)

fibForTens :: [Int]
fibForTens = map fib2 [x * 10 | x <- [1..10]]

sum' :: Num a => [a] -> a
sum' []   = 0
sum' (x:xs) = x + sum' xs

prod' :: Num a => [a] -> a -- prod' [1,2,3] = 6
prod' [] = 1
prod' (h:xs) = h * prod' xs

length' :: Num b  => [a] -> b --length' [1,1,1,1] = 4
length' [] = 0
length' (h:xs) = 1 + length' xs

or' :: [Bool] -> Bool -- or' [True, False, True] = True
or' [] = False
or' (h:xs) = h || or' xs

and' :: [Bool] -> Bool -- and' [True, False, True] = False
and' [] = True
and' (h:xs) = h && and' xs

elem' :: Eq a => a -> [a] -> Bool -- elem' 3 [1,2,3] = True
elem' _ [] = False
elem' x (h:xs) = (x == h) || elem' x xs

doubleAll :: Num t => [t] -> [t] -- double doubleAll [1,2] = [2,4]
doubleAll [] = []
doubleAll (h:xs) = (2*h) : doubleAll xs

squareAll :: Num t => [t] -> [t] -- double squareAll [2,3] = [4,9]
squareAll [] = []
squareAll (h:xs) = (h^2) : squareAll xs

selectEven :: Integral t => [t] -> [t] -- selectEven [1,2,3] = [2]
selectEven [] = []
selectEven (h:xs) = if (h `mod` 2 == 0) then h : res else res
            where res = selectEven xs

avg :: (Num t, Fractional t) => [t] -> t
avg xs = sum' xs / length' xs 

geomAvg :: [Double] -> Double -> Double
geomAvg xs n = (prod' xs) ** n

avgPair :: [Double] -> Double -> (Double, Double)
avgPair xs n = (geomAvg xs n, avg xs)


sum'2 :: Num a => [a] -> a
sum'2 xs = loop 0 xs
 where loop acc []     = acc
       loop acc (x:xs) = loop (x + acc) xs

sum'3 :: Num a => [a] -> a
sum'3 = loop 0
 where loop acc []     = acc
       loop acc (x:xs) = loop (x + acc) xs

sum'4 :: Num a => [a] -> a
sum'4 = loop 0
   where loop !acc []     = acc
         loop !acc (x:xs) = loop (x + acc) xs

prod'2 :: Num a => [a] -> a
prod'2 = loop 1
    where loop acc [] = acc
          loop acc (x:xs) = loop (x * acc) xs

length'2 :: [a] -> Int
length'2 = loop 0
    where loop acc [] = acc 
          loop acc (x:xs) = loop(1 + acc) xs

main = do 
    print (fib2 5)
    print (fibForTens)
    print (prod' [1,2,3])
    print (length' [1,1,1,1])
    print (or' [True, False, True])
    print (and' [True, False, True])
    print (elem' 3 [1,2,3])
    print (doubleAll [1,2])
    print (squareAll [2,3])
    print (selectEven [1,2,3])
    print (avg [1,2,3,4])
    print (geomAvg [2,2,5,7] (1/4))
    print (avgPair [2,2,5,7] (1/4))
    print (sum'2 [1,2,3])
    print (sum'3 [1,2,3])
    print (prod'2 [1,2,3])
    print (length'2 [1,2,3])
    print (sum'4 [1,2,3])
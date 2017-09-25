sqr x = x^2

funcFactory n = case n of
     1 -> id
     2 -> sqr
     3 -> (^3)
     4 -> \x -> x^4
     5 -> intFunc
     _ -> const n
    where
           intFunc x = x^5

sum' :: Num a => [a] -> a
sum' []     = 0
sum' (x:xs) = x + sum' xs

factorial :: Int -> Int 
factorial x | x <= 0 = 1
factorial x = x * factorial (x-1)

expApproxUpTo :: Int -> Double -> Double
expApproxUpTo n x = sum' [x ^ k / fromIntegral(factorial k) | k <- [0..n]] 

main = do 
    print (expApproxUpTo 2 2.0)
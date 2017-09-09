sum' :: Num a => [a] -> a
sum' []     = 0
sum' (x:xs) = x + sum' xs

sumSqr' :: [Float] -> Float
sumSqr' [] = 0
sumSqr' (x : xs) = (x ^ 2) + (sumSqr' xs)

sumWith :: Num a => (a -> a) -> [a] -> a
sumWith _ [] = 0
sumWith f (x:xs) = f x + (sumWith f xs) 

sum'' = sumWith (\x->x)
sumSqr = sumWith (\x->x ** 2)
sumCube = sumWith (\x->x ** 3)
sumAbs = sumWith (\x-> if x >= 0 then x else -x)
listLength = sumWith (\x->1)

generalWith :: (Float->Float)->(Float->Float->Float)->Float->[Float] -> Float
generalWith _ _ neutral [] = neutral
generalWith fx fr neutral (x:xs) = fr (fx x) (generalWith fx fr neutral xs)

sumSqrt = sumWith (\x -> x ** (1/2))

main = do
    print (sum' [1,2,3])
    print (sumSqr' [1,2])
    print (sum''[1, -2])
    print (sumWith (\x->x ** 5) [1..15])
    print (listLength [1..14])
    print (generalWith (\x -> x) (*) 1 [1..4])
    print (sumSqrt [1,9,16])
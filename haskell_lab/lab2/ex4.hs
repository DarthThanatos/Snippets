import Data.Char

isPalindrome :: [Char] -> Bool
isPalindrome [] = True
isPalindrome x | length x == 1 = True 
isPalindrome x = head x == last x && isPalindrome (init (tail x))

getElemIdxHelper :: [a] -> Int -> Int -> a
getElemIdxHelper [] _ _ = error "Out of bounds exception"
getElemIdxHelper xs index acc = if (acc == index ) then head xs else getElemIdxHelper (tail xs) index (acc+1)

getElemAtIdx_ :: [a] -> Int -> a
getElemAtIdx_ xs index = getElemIdxHelper xs index 0

getElemAtIdx :: [a] -> Int -> a 
getElemAtIdx xs index | index >= length xs || index < 0 = error "Out of bounds exception"
getElemAtIdx xs index = head (drop index xs)

capitalize :: [Char] -> [Char]
capitalize [] = []
capitalize w | length w == 1 = [toUpper (head w)]
capitalize w = capitalize (init w) ++ [toLower (last w)]

triangleExpr :: [(Int, Int, Int)]
triangleExpr = [(a,b,c) | a <- [1..10], b <- [a..10], c <- [b..10], a ^ 2 + b ^ 2 == c ^ 2]

isPrime :: Integral t => t -> Bool
isPrime n | n <= 1 = False
isPrime n = [i | i <- [2..n-1], n `mod` i == 0] == []

primes :: [Int]
primes = eratoSieve [2..]
 where
   eratoSieve :: [Int] -> [Int]
   eratoSieve (p : xs) = p : eratoSieve [x | x <- xs, x `mod` p /= 0]

isPrime_ :: Int -> Bool
isPrime_ n | n <= 1 = False
isPrime_ n = eratoCheck n primes
            where 
                eratoCheck :: Int -> [Int] -> Bool
                eratoCheck x xs =
                                if (head xs < x) then eratoCheck x (tail xs) 
                                else 
                                    if (head xs == x) then True 
                                    else False
countPrimesToN :: Int -> Int 
countPrimesToN n | n <= 1 = 0
countPrimesToN n = length [a | a <- [1..n], isPrime_ a]

countPrimes :: Int 
countPrimes = length [a | a <- [1..1000], isPrime a]

allEqual :: Eq a => [a] -> Bool
allEqual [] = True
allEqual xs = [x | x <- xs, x /= head xs] == [] -- allEqual [1,1] = True, allEqual [1,2] = False

main = do 
    print (isPalindrome "ABBA")
    print (getElemAtIdx "ABCD" (3))
    print (capitalize "sadQQEQ")
    print (length triangleExpr)
    print (take 10 primes)
    print (isPrime 28)
    print (isPrime_ 28)
    print countPrimes
    print (countPrimesToN 1000)
    print (allEqual ([1,3,0,2] :: [Int]))
import Data.Char
import Data.List
import Data.Set

capitalize :: [Char] -> [Char]
capitalize [] = []
capitalize (x:xs) = toUpper x : (Prelude.map toLower xs)

formatStr s = foldr1 (\w s -> w ++ " " ++ s) .
          Prelude.map capitalize .
          Prelude.filter (\x -> length x > 1) $
          words s

prodPrices p = case p of
 "A" -> 100
 "B" -> 500
 "C" -> 1000
 _   -> error "Unknown product"

products = ["A","B","C"]

-- basic discount strategy
discStr1 p
 | price > 999 = 0.3 * price
 | otherwise   = 0.1 * price
 where price = prodPrices p

-- flat discount strategy
discStr2 p = 0.2 * prodPrices p

totalDiscout discStr =
 foldl1 (+) .
 Prelude.map discStr .
 Prelude.filter (\p -> prodPrices p > 499)

pipe1v1 = replicate 2 . product . Prelude.map (*3) $ zipWith max [4,2] [1,5]
pipe1v2 = (replicate 2 . product . Prelude.map (*3)) (zipWith max [4,2] [1,5])

pipe2v1 = sum . takeWhile (<1000) . Prelude.filter odd . Prelude.map (^2) $ [1..] 
pipe2v2 = (sum . takeWhile (<1000) . Prelude.filter odd . Prelude.map (^2) ) [1..] 

pipe3v1 = length . fromList . Prelude.map toLower $ "thirteen men must go" 
pipe3v2 = (length . fromList . Prelude.map toLower)  "thirteen men must go"

boobies = ((.).(.)) (\x -> x + 1) (\x y-> x + y) 1 1


main = do 
    print (formatStr "Dzien dobry czesc i czolem pytacie skad sie wzialem")
    print (totalDiscout discStr1 ["A", "B", "C"])
    print (totalDiscout discStr2 ["A", "B", "C"])
    print $ pipe1v1
    print $ pipe1v2
    print $ pipe2v1
    print $ pipe2v2
    print $ pipe3v1
    print $ pipe3v2 
    print $ boobies

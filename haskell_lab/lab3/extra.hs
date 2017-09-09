import Data.Char

fun = 
    sum .
    map ( foldl1 ( \x y -> 2*x + y ) ) .
    map ( map digitToInt ) .
    filter ( all ( `elem` "01" ) ) .
    words

prodWith _ [] = 1                  --rekurencja
prodWith f (x:xs) = f x * prodWith f xs

prodWith' f xs = product [f x | x <- xs]            --list comprehensions
prodWith'' f xs = foldr1 (\x acc-> acc * f x)  (xs ++ [1])   --foldr1


main = do
    print $ fun "To 100 zdanie nie10 ma 1010 11 sensu" -- 100 1010 11 -> 17
    print $ prodWith (^2) [1..3]
    print $ prodWith' (^2) [1..3]
    print $ prodWith'' (^2) [1..3]
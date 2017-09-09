import Data.List

sortDesc :: Ord a => [a] -> [a]
sortDesc xs = (reverse . sort) xs

are2FunsEqAt :: Eq a => (t -> a) -> (t -> a) -> [t] -> Bool
are2FunsEqAt f g xs = [f x | x <- xs] == [g x | x <- xs] -- are2FunsEqAt (+2) (\x -> x + 2) [1..1000] = True

composeFunList :: [a -> a] -> (a -> a)
composeFunList xs = foldl (\x y -> x . y) id xs

custom_f :: Int -> Int
custom_f x = x + 1
custom_g :: Int -> Int
custom_g x = x * 2

custom_h :: Int -> Int
custom_h x = x ^ 3

infixl 9 >.>
(>.>) :: (a -> b) -> (b -> c) -> (a -> c)
g >.> f = f . g

custom_dolar_fun = (custom_f . ($) custom_g . custom_h) 3
custom_fun = custom_f . custom_g . custom_h

main = do
    print "Hello"
    print (sortDesc [1, -3, 0, -5, 4, 3, 8])
    print (are2FunsEqAt (+2) (\x -> x + 2) [1..1000] )
    print (composeFunList [(*2), (+1), (^3)] 2) 
    print (custom_g . custom_f . custom_h $ 2)
    print (custom_g >.> custom_f >.> custom_h $ 2)
    print (custom_fun 3) -- = 55
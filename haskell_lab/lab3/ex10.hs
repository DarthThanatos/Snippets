isSortedAsc :: Ord a => [a] -> Bool
isSortedAsc xs = filter (\(x, y) -> x > y) (zip xs (tail xs))  == [] -- isSortedAsc [1,2,2,3] -> True, isSortedAsc [1,2,1] -> False


--everySecond :: [t] -> [t]
--everySecond xs =  -- everySecond [1..8] -> [1,3,5,7]

zip3' :: [a] -> [b] -> [c] -> [(a,b,c)]
zip3' xs ys zs = map (\((x,y),(y',z)) -> (x,y,z)) (zip (zip xs ys) (zip ys zs))

fst' (res,_,_) = res
snd' (_,res,_) = res
trd' (_,_,res) = res

unzip3' :: [(a, b, c)] -> ([a], [b], [c])
unzip3' [] = ([],[],[])
unzip3' ((x,y,z):xs) = (x:(fst' unzipped), y:(snd' unzipped), z:(trd' unzipped))
 where unzipped = unzip3' xs


main = do 
    print (isSortedAsc [1,2,2,3])
    print (isSortedAsc [1,2,1])
    print (zip3' [1,2,3] "abc" "xyz")
    print (unzip3' [(1,"a","x"),(2,"b","y"),(3,"c","z")])
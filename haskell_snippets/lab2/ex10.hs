fst2Eq :: Eq a => [a] -> Bool
fst2Eq (x : y : _) | x == y = True
fst2Eq _                    = False

fstPredecessor :: (Num a, Eq a) => [a] -> Bool
fstPredecessor (x : y : _) | x + 1 == y = True
fstPredecessor _ = False

fstDivisor :: [Int] -> Bool
fstDivisor (x:_:y:_) | y `mod` x == 0  = True
fstDivisor _ = False

main = do 
    print (fst2Eq [1,1,3,4,3])
    print(fstPredecessor [1,2])
    print (fstDivisor([5,2,3,4]))
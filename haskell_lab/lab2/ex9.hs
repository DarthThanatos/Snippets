qSort :: Ord a => [a] -> [a]
qSort []     = []
qSort (x:xs) = qSort (leftPart xs) ++ [x] ++ qSort (rightPart xs)
 where
   leftPart  xs = [ y | y <- xs, y <= x ]
   rightPart xs = [ y | y <- xs, y > x  ]

qSort_filter :: Ord a => [a] -> [a]
qSort_filter []     = []
qSort_filter (x:xs) = qSort_filter (leftPart xs) ++ [x] ++ qSort_filter (rightPart xs)
 where
   leftPart  xs = filter (<= x) xs
   rightPart xs = filter (> x) xs

insertionSort :: Ord a => [a] -> [a]
insertionSort xs = insertionSortAux [] xs
    where
        insertionSortAux :: Ord a => [a] -> [a] -> [a]
        insertionSortAux sorted [] = sorted
        insertionSortAux sorted (x:xs) = insertionSortAux ((smaller sorted x) ++ [x] ++ (bigger sorted x)) xs
            where 
                smaller :: Ord a => [a] -> a -> [a]
                bigger :: Ord a => [a] -> a -> [a]
                smaller sorted x = [s | s <- sorted, s <= x]
                bigger sorted x = [b | b <- sorted, b > x]



splitListInHalf :: [a] -> ([a],[a])
splitListInHalf xs = splitListInHalfAux ([],[]) xs
    where 
        splitListInHalfAux :: ([a], [a]) -> [a] -> ([a],[a])
        splitListInHalfAux (firstHalf, secondHalf) [] = (firstHalf, secondHalf)
        splitListInHalfAux (firstHalf, secondHalf) xs | length xs == 1 = (firstHalf ++ xs, secondHalf)
        splitListInHalfAux (firstHalf,secondHalf) xs = splitListInHalfAux (firstHalf ++ [head xs], [last xs] ++ secondHalf) (tail (init xs))

mergeSort :: Ord a => [a] -> [a]
mergeSort xs | length xs <= 1 = xs
mergeSort xs = mergeSortAux (mergeSort (fst splitted), mergeSort (snd splitted))
    where 
        splitted = splitListInHalf xs

        mergeSortAux :: Ord a => ([a],[a]) -> [a]
        mergeSortAux ([], []) = []
        mergeSortAux ([], secondHalf) = [head secondHalf]  ++  mergeSortAux ([], tail secondHalf) 
        mergeSortAux (firstHalf, []) = [head firstHalf]  ++ mergeSortAux (tail firstHalf, [])
        mergeSortAux (firstHalf, secondHalf) = if head firstHalf <= head secondHalf then [head firstHalf] ++ mergeSortAux (tail firstHalf, secondHalf)
                                               else  [head secondHalf] ++ mergeSortAux (firstHalf, tail secondHalf)

main = do 
    print (qSort [-3, 9, -2, 1, -8, 0])
    print (qSort_filter [-3, 9, -2, 1, -8, 0])
    print (insertionSort [-3, 9, -2, 1, -8, 0])
    print (splitListInHalf ([-3, 9, -2, 1, -8, 0] :: [Int]))
    print (mergeSort ([1,-2,3] :: [Int]))
concat' :: [[a]] -> [a]
concat' []     = []
concat' (x:xs) = x ++ concat' xs

concat_optionOne xs = concatMap (\x -> [2*x]) xs
concat_optionTwo xs = concatMap (\x -> x ++ "!") xs

main = do 
    print (concat' ["abc", "def"])
    print (concat' [[1,2],[3,4]])
    print ((concat' . concat') [ [[1,2], [3,4]] , [[5,6], [7,8]] ])
    print (concat_optionOne [1..5])
    print (concat_optionTwo ["Ready", "Steady", "Go"])
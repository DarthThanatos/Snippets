funcList :: [ Double -> Double ]
funcList = [ \x -> (sin x)/x, \x -> log x + sqrt x + 1, \x -> (exp 1) ** x ]

evalFuncListAt :: a -> [a -> b] -> [b]
evalFuncListAt x [] = []
evalFuncListAt x (f:fs) = f x : evalFuncListAt x fs

displEqs :: (Double -> Double, Double -> Double)
displEqs = (\t -> 4 * t^2 + 2 * t, \t -> 3 * t^2)

main = do 
    print (evalFuncListAt 1 funcList)
    print (evalFuncListAt (-3) [ id, abs, const 5, \y -> 2 * y + 8 ])
    print (fst displEqs 1)
    print (snd displEqs 1)
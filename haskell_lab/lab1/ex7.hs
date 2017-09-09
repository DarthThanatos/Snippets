not' :: Bool -> Bool
not' True = False
not' False = True

isItTheAnswer :: String -> Bool
isItTheAnswer "Love" = True -- :)
isItTheAnswer _      = False

or' :: (Bool, Bool) -> Bool
or' (True,_) = True
or' (False, x) = x 

and' :: (Bool, Bool) -> Bool 
and' (True, x) = x 
and' (False, _) = False 

nand' :: (Bool, Bool) -> Bool
nand' (True,True) = False 
nand' (_,_) = True

xor' :: (Bool, Bool) -> Bool
xor' (x,y) = if x /= y then True else False

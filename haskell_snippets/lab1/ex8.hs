not' :: Bool -> Bool 
not' b = case b of 
	True -> False 
	False -> True
	
absInt n = 
	case (n >= 0) of 
		True -> n 
		_ -> - n
		
isItTheAnswer :: String -> Bool
isItTheAnswer b = case (b == "Love") of 
	True -> True 
	_ -> False

	
or' :: (Bool, Bool) -> Bool
or' (x,y) = case (x,y) of 
	(True, _) -> True 
	(False, x) -> x 
	
and' :: (Bool, Bool) -> Bool
and' (x,y) = case (x,y) of 
	(True, x) -> x 
	(False, _) -> False

nand' :: (Bool, Bool) -> Bool
nand' (x,y) = case (x,y) of 
	(True, True) -> False 
	(_,_) -> True 

xor' :: (Bool, Bool) -> Bool
xor' (x,y) = case (x,y) of 
	(True, True) -> False 
	(False, False) -> False 
	(_,_) -> True 
	
col = [(True,True), (True, False), (False, True), (False, False)]
	
main :: IO()
main = do
	print ( "or: " ++ show [(x, or' x) | x <- col])
	print ( "and: " ++ show [(x, and' x) | x <- col])
	print ( "xor: " ++ show [(x, xor' x) | x <- col])
	print ( "nand: " ++ show [(x, nand' x) | x <- col])
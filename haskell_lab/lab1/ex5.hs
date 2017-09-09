sgn :: Int -> Int 
sgn n = if n < 0
    then -1 
    else if n == 0 
        then 0
        else one
        where one = 1
			
absInt :: Int -> Int
absInt a = if a >= 0 then a else -a

min2Int :: (Int, Int) -> Int
min2Int (x,y) = if x <= y then x else y

min3Int :: (Int, Int, Int) -> Int
min3Int (x,y,z) = if x <= min2Int(y,z) then x else if y <= min2Int(x,z) then y else z

toUpper :: Char -> Char
toLower :: Char -> Char

toUpper x = toEnum( fromEnum x + fromEnum 'A' - fromEnum 'a') 
toLower x = toEnum( fromEnum x + fromEnum 'a' - fromEnum 'A') 

isDigit :: Char -> Bool
charToNum :: Char -> Int

isDigit x = if  num >= 0 && num <= 9 then True else False where num = fromEnum x - fromEnum '0'

charToNum x = if isDigit x  then fromEnum x - fromEnum '0' else err where err = -1

main = do print "Hello"
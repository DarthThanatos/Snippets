myFun x = 2 * x

add2T :: Num a => (a,a) -> a
add2T (x,y) = x + y

add2C:: Num a => a -> a-> a 
add2C x y = x + y

add3T :: Num a => (a,a,a) -> a 
add3T (x,y,z) = x + y + z

add3C :: Num a => a -> a -> a -> a 
add3C x y z = x + y + z

curry2 :: ((a,b) -> c) -> a -> b -> c 
curry2 f x y = f (x, y)

uncurry2 :: (a -> b -> c) -> (a,b) -> c 
uncurry2 f (x, y) = f x y 

curry3 :: ((a,b,c) -> d) -> a ->b -> c ->d
curry3 f x y z = f(x,y,z)

uncurry3 :: (a -> b -> c -> d) -> (a, b, c) -> d
uncurry3 f (x,y,z) = f x y z  


fiveToPower_ :: Integer -> Integer
fiveToPower_ = (5 ^)

_ToPower5 :: Num a => a -> a 
_ToPower5 = (^ 5)

subtrNFrom5 :: Num a => a -> a
subtrNFrom5 = (5 -)

subFun :: Num a => a -> a -> a
subFun x y = x - y


flip2 :: (a -> b -> c) -> b -> a -> c 
flip2 f x y = f y x

flip3 :: (a->b->c->d) -> c -> b -> a -> d 
flip3 f x y z = f z y x

subtr5From_ :: Num a => a -> a
subtr5From_ x = flip2 subFun 5 x 

sub3Fun :: Num a => a -> a -> a -> a
sub3Fun x y z = x - y - z

main = do 
    print (curry3 add3T 1 2 3)
    print (uncurry3 add3C (1,2,3))
    print (fiveToPower_ 3)
    print (_ToPower5 1)
    print (subtrNFrom5 3)
    print (subtr5From_ 8)
    print (sub3Fun 3 2 1)
    print (flip3 sub3Fun 3 2 1)
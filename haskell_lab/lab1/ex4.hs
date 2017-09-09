sqr:: Double -> Double 
sqr x= x *x

vec3DLen :: (Double, Double, Double) -> Double
vec3DLen (x,y,z) = sqrt(x ** 2 +y ** 2 + z ** 2)

vec2DLen :: (Double, Double) -> Double
vec2DLen (x, y) = sqrt (x^2 + y^2)

swap :: (Int,Char) ->  (Char, Int)
swap (x,y) = (y,x)

threeEqual :: (Int, Int, Int) -> Bool
threeEqual (x, y ,z )= x==y && y==z

sumThree:: Double->Double->Double  -> Double
sumThree a b c = a + b + c

aPbMc :: Double->Double->Double  -> Double
aPbMc a b c = a + b - c

heron :: (Double,Double,Double)->Double
heron(a,b,c) = sqrt(sumThree a b c * aPbMc a b c  * aPbMc a c b * aPbMc b c a)/4
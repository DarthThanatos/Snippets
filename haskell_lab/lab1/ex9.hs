roots :: (Double, Double, Double) -> (Double, Double)
roots (a, b, c) = ( (-b - d) / e, (-b + d) / e )
   where d = sqrt (b * b - 4 * a * c)
         e = 2 * a

unitVec2D :: (Double, Double) -> (Double, Double)
unitVec2D (x,y) = (x/len, y/len) where len = sqrt(x ** 2 + y ** 2)

unitVec3D :: (Double, Double, Double) -> (Double, Double, Double)
unitVec3D (x,y,z) = (x / len, y / len, z / len) where len = sqrt(x **2 + y ** 2 + z ** 2)

heron :: (Double,Double,Double)->Double
heron(a,b,c) = sqrt(sumThree a b c * aPbMc a b c  * aPbMc a c b * aPbMc b c a)/4
	where 
		sumThree a b c = a + b + c 
		aPbMc a b c = a + b - c

main = do 
    print (roots(1,0,-1))
    print (unitVec2D (1,1))
    print (unitVec3D(1,1,1))
    print (heron(2,2,2))
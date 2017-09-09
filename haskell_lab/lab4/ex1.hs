type CartesianCoord' a = (a,a)
type PolarCoord' a = (a,a)

polarToCartesian' :: Floating a => PolarCoord' a -> CartesianCoord' a
polarToCartesian' (r,phi) = (r * cos phi, r * sin phi)

polarToCartesian :: Floating a => (a,a) -> (a,a)
polarToCartesian (r,phi) = (r * cos phi, r * sin phi)

type Name' = String
type Surname' = String
type Address' = String
type PersonInfo' = (Name', Surname', Address')
type PersonInfoToStringType' = PersonInfo' -> String

personInfoToString :: (String,String,String) -> String
personInfoToString (nm,snm,addr) =
 "name: " ++ nm ++ ", surname: " ++ snm ++ ", addr: " ++ addr

personInfoToString' :: PersonInfo' -> String
personInfoToString' (name, surname, address) = 
    "name: " ++ name ++ ", surname: " ++ surname ++ ", address: " ++ address 
 
main = do 
    print $ polarToCartesian' (1, pi)
    print $ personInfoToString ("Michail", "Berlioz", "ul. Sadowa 302a, m.50")
    print $ personInfoToString ("ul. Sadowa 302a, m.50", "Stiopa", "Lichodiejew")
    print $ personInfoToString'  ("ul. Sadowa 302a, m.50", "Stiopa", "Lichodiejew")
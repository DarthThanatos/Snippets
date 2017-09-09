module Ex1 where 

(<$<) :: (a -> b) -> a -> b
(<$<) = ($)

(>$>) :: a -> (a -> b) -> b
x >$> f = f x
infixl 0 >$>

------------------------------------------------
(<.<) :: (b -> c) -> (a -> b) -> (a -> c)
(<.<) = (.)

(>.>) :: (a -> b) -> (b -> c) -> (a -> c)
f >.> g = g . f
infixl 9 >.>

safeTail :: [a] -> Maybe [a]
safeTail []     = Nothing
safeTail (x:xs) = Just xs
-------------------------------------------------
extractMaybe :: Maybe a -> a
extractMaybe Nothing  = error "Nothing inside!"
extractMaybe (Just x) = x

-------------------------------------------------
insertMaybe :: a -> Maybe a
insertMaybe = Just

-------------------------------------------------
(>^$>) :: Maybe a -> (a -> Maybe b) -> Maybe b
Nothing >^$> _ = Nothing
(Just x) >^$> f = f x
infixl 1 >^$>

-------------------------------------------------
f1 :: (Ord a, Num a) => a -> Maybe a
f1 x = if x > 0 then Just (x + 1) else Nothing

f2 :: (Eq a, Num a) => a -> Maybe a
f2 x = if x /= 0 then Just (10 * x) else Nothing


-- Kleisli composition
(>.>>) :: (a -> Maybe b) -> (b -> Maybe c) -> (a -> Maybe c)
-- f >.>> g = \x -> g (extractMaybe (f x))
-- f >.>> g = \x -> f x >^$> g
f >.>> g = \x -> g $ extractMaybe $ joinMaybe $ fmap f $ Just x

joinMaybe :: Maybe (Maybe a) -> Maybe a
joinMaybe Nothing = Nothing
joinMaybe (Just Nothing) = Nothing
joinMaybe (Just (Just x))= Just x

--------------------------------------------------
extract :: (b,String) -> b
extract (y, s) = y

(>^$->)  :: (a,String) -> (a -> (b, String)) -> (b, String)
(x, s1) >^$-> f = f x

insert :: b -> String -> (a -> (b,String))
insert y s = \x -> (y,s)


(>.->>) :: (a -> (b, String)) -> (b -> (c, String)) -> a -> (c,String)
-- f >.->> g = \x -> case f x of (b,s) -> g b
f >.->> g = \x -> g $ extract $ f x  
---------------------------------------------------

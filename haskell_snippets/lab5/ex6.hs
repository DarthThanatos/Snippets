{-# LANGUAGE DeriveFunctor #-}

newtype Box a = MkBox a deriving (Show, Functor)

data MyList a = EmptyList |
                Cons a (MyList a) deriving (Show, Functor)

data BinTree a = EmptyBT | NodeBT a (BinTree a) (BinTree a) deriving (Show, Functor)


{-
instance Functor BinTree where 
    fmap _ EmptyBT = EmptyBT
    fmap f (NodeBT v left right) = NodeBT (f v) (fmap f left) (fmap f right)
-}
-- t = (NodeBT 10 (NodeBT 5 (NodeBT 4 EmptyBT EmptyBT) (NodeBT 6 (NodeBT 5 EmptyBT EmptyBT) (NodeBT 7 EmptyBT EmptyBT))) (NodeBT (-1) (NodeBT (-10) EmptyBT EmptyBT) (NodeBT 3 EmptyBT EmptyBT)))

{-instance Functor MyList where
    fmap _ EmptyList = EmptyList
    fmap f (Cons x mxs) = Cons (f x) (fmap f mxs)
-}
{-
instance Functor Box where
        fmap f (MkBox x) = MkBox (f x)
-}
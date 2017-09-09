data MyInt = MkMyInt Int

instance Eq MyInt where 
    (==) (MkMyInt i1) (MkMyInt i2) = i1 == i2

instance Ord MyInt where 
    (<=) (MkMyInt i1) (MkMyInt i2) = i1 <= i2

data BinTree a = EmptyBT |
                 NodeBT a (BinTree a) (BinTree a) deriving Show

instance Eq a => Eq (BinTree a) where 
    (==) EmptyBT EmptyBT = True
    (==) (NodeBT v1 left1 right1) (NodeBT v2 left2 right2) = 
        v1 == v2 && left1 == left2 && right1 == right2 
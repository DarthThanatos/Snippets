package mirrored.bobsrockets{
    
    class Ship{
        val nav = new navigation.Navigator
    }

    package navigation{
        class Navigator{
            val map = new StarMap
        }
        class StarMap 
    }

    package fleets{
        class Fleet{
            def addShip(){new Ship}
        }
    }
}
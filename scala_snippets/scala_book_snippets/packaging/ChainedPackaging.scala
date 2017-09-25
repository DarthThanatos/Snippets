package bobsrockets
class Ship{
    val nav = new Navigator
}
package bobrockets.navigation
class Navigator{
    val map = new StarMap
}
class StarMap 



package bobsrockets.fleets
class Fleet{
    def addShip(){new Ship}
}

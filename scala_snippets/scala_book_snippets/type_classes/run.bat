call scalac -d bin Statistics.scala DefaultNumberLikes.scala
call scalac -d bin -cp bin;joda_time.jar;joda_convert.jar JodaImplicits.scala TypeClassMain.scala
call scala -cp bin;joda_time.jar;joda_convert.jar TypeClassMain
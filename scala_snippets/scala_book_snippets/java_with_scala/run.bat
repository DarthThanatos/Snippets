call javac -d bin MyIgnore.java 
call javac -d bin Wild.java
call scalac -d bin -cp bin Tests.scala 
call scala -cp bin tests.MainTest
call scala -cp bin existential_type.scala
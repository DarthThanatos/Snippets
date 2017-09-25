call fsc -d bin -cp scalatest.jar;junit3.jar;..\text_layout\bin\ ElementTest.scala 
call fsc -d bin -cp junit3.jar;..\text_layout\bin\  JUnitTest.scala 
call fsc -d bin -cp specs.jar;scalaz.jar;scalaz-concurrent.jar;..\text_layout\bin\ SpecsCheck.scala
call fsc -d bin -cp scalatest.jar;testng.jar;..\text_layout\bin\ TestNG.scala
call fsc -d bin -cp scalacheck.jar;scalatest.jar;..\text_layout\bin\ TestScalaCheck.scala

rem =========================================================================================

call scala -cp scalatest.jar;junit3.jar;..\text_layout\bin\;bin  org.scalatest.tools.Runner ^
     -s ElementSuite -s ElementFunSuite -s ElementFlatMatcherSpec -s ElementJUnit3Suite 

call scala -cp junit3.jar;..\text_layout\bin\;bin junit.textui.TestRunner ElementTestCase

call scala -cp specs.jar;scalaz.jar;scalaz-concurrent.jar;..\text_layout\bin\;bin specs2.run HelloWorldSpec  

call scala -cp scalatest.jar;testng.jar;jcommander.jar;..\text_layout\bin\;bin org.testng.TestNG -testclass ElementJUnit4Tests
call scala -cp scalatest.jar;testng.jar;jcommander.jar;..\text_layout\bin\;bin org.scalatest.run ElementTestNGTraitSuite

call scala -cp scalacheck.jar;scalatest.jar;..\text_layout\bin\;bin org.scalatest.tools.Runner -s ElementPropSpec
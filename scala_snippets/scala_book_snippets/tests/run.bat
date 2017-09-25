call fsc -d bin -cp scalatest.jar;junit3.jar;..\text_layout\bin\ ElementTest.scala 
call fsc -d bin -cp junit3.jar;..\text_layout\bin\  JUnitTest.scala 

call fsc -d bin -cp specs.jar;scalaz.jar;..\text_layout\bin\ SpecsCheck.scala
rem call fsc -d bin -cp hamcrest.jar;junit4;..\text_layout\bin\ TestNG.scala
call fsc -d bin -cp scalacheck.jar;scalatest.jar;..\text_layout\bin\ TestScalaCheck.scala

rem =========================================================================================

call scala -cp scalatest.jar;junit3.jar;..\text_layout\bin\;bin  org.scalatest.tools.Runner ^
     -s ElementSuite -s ElementFunSuite -s ElementFlatMatcherSpec -s ElementJUnit3Suite 

call scala -cp junit3.jar;..\text_layout\bin\;bin junit.textui.TestRunner ElementTestCase

rem call scala -cp scalaz.jar;specs.jar;..\text_layout\bin\;bin specs2.run ElementSpecification

rem call scala -cp scalatest.jar;hamcrest.jar;junit4;jar;..\text_layout\bin\;bin org.junit.runner.JUnitCore ElementJUnit4Tests

rem call scala -cp scalacheck.jar;scalatest.jar;..\text_layout\bin\;bin ElementPropSpec
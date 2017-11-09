
name := "shop_lab4"

version := "0.1"

scalaVersion := "2.12.3"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.6",
  "com.typesafe.akka" % "akka-testkit_2.12" % "2.5.6",
  "com.typesafe.akka" %% "akka-persistence" % "2.5.4",
  "org.scalatest" % "scalatest_2.12" % "3.0.4" % "test",
  "org.iq80.leveldb"            % "leveldb"          % "0.9",
  "org.fusesource.leveldbjni"   % "leveldbjni-all"   % "1.8"
)
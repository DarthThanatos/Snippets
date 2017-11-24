name := "shop_lab6"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.6",
  "com.typesafe.akka" %% "akka-remote" % "2.5.6",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.6" % "test",
  "com.typesafe.akka" %% "akka-persistence" % "2.5.6",
  "org.scalatest" % "scalatest_2.12" % "3.0.4" % "test",
  "org.iq80.leveldb"            % "leveldb"          % "0.9",
  "org.fusesource.leveldbjni"   % "leveldbjni-all"   % "1.8",
  "com.github.tototoshi" %% "scala-csv" % "1.3.5",
  "com.typesafe.akka" %% "akka-http" % "10.0.10",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.10",
  "com.typesafe.akka" %% "akka-cluster" % "2.5.7",
  "com.typesafe.akka" %% "akka-cluster-tools" % "2.5.7"

)
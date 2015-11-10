name := """ice-road"""

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"

libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.0.0"

mainClass in (Compile, run) := Some("IceRoadMain")
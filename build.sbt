name := "AirportProject"
version := "0.1"
scalaVersion := "2.13.10"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.15" % Test,
  "com.h2database" % "h2" % "2.1.214" % Optional,
  "com.typesafe.slick" %% "slick" % "3.4.1" % Optional, 
  "org.scalafx" %% "scalafx" % "20.0.0-R31"
)

TaskKey[Unit]("checkJavaVersion") := {
  val javaVersion = System.getProperty("java.version")
  println(s"Java version: $javaVersion")
}
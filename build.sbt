name := """meeting-calendar-assistant"""
organization := "peeyush"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.4"

libraryDependencies += guice

libraryDependencies ++= Seq(
  javaWs,
  "org.mongodb" % "mongo-java-driver" % "3.12.8",
  "org.mongodb.morphia" % "morphia" % "1.3.2",
  "commons-lang" % "commons-lang" % "2.6"
)
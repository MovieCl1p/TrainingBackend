ThisBuild / organization := "com.training"
ThisBuild / version := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
ThisBuild / scalaVersion := "2.13.8"

// Workaround for scala-java8-compat issue affecting Lagom dev-mode
// https://github.com/lagom/lagom/issues/3344
ThisBuild / libraryDependencySchemes +=
  "org.scala-lang.modules" %% "scala-java8-compat" % VersionScheme.Always

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.3" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.1.1" % Test

lazy val `backend` = (project in file("."))
  .aggregate(`backend-api`, `backend-impl`, `backend-stream-api`, `backend-stream-impl`)

lazy val `backend-api` = (project in file("backend-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `backend-impl` = (project in file("backend-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings)
  .dependsOn(`backend-api`)

lazy val `backend-stream-api` = (project in file("backend-stream-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `backend-stream-impl` = (project in file("backend-stream-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .dependsOn(`backend-stream-api`, `backend-api`)

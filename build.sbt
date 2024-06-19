ThisBuild / organization := "com.bokun"
ThisBuild / version      := "0.1-SNAPSHOT"

name := "sbt-swagger-play"

lazy val play27 = PlayAxis("2.7.9")
lazy val play28 = PlayAxis("2.8.7")
lazy val play288 = PlayAxis("2.8.8")
lazy val play29 = PlayAxis("2.9.0")
lazy val play30 = PlayAxis("3.0.0")

lazy val scala212 = "2.12.13"
lazy val scala213 = "2.13.4"
lazy val scala3 = "3.3.1"

val swaggerVersion = "2.2.22"
val playVersion = "2.8.17"

lazy val swaggerPlayVersion = "4.0.0"

def scalaJava8Compat(scalaVersion: String) = "org.scala-lang.modules" %% "scala-java8-compat" %
  (CrossVersion.partialVersion(scalaVersion) match {
    case Some((2, major)) if major >= 13 => "1.0.2"
    case _                               => "0.9.1"
  })

lazy val root = (project in file("."))
  .aggregate(plugin.projectRefs: _*)
  .aggregate(runner.projectRefs: _*)
  .settings(
    name := "sbt-swagger-play",
    publish / skip := true,
    libraryDependencies ++= Seq(
      scalaJava8Compat(scalaVersion.value),
      "org.scalatest" %% "scalatest" % "3.2.17" % Test,
      "ch.qos.logback" % "logback-classic" % "1.2.10" % Provided,
      "org.slf4j" % "slf4j-simple" % "1.7.26" % Provided,
      "io.swagger.core.v3" % "swagger-core" % swaggerVersion,
      "io.swagger.core.v3" % "swagger-annotations" % swaggerVersion,
      "io.swagger.core.v3" % "swagger-models" % swaggerVersion,
      "io.swagger.core.v3" % "swagger-jaxrs2" % swaggerVersion,
      "javax.ws.rs" % "javax.ws.rs-api" % "2.0.1",
      "com.github.pureconfig" %% "pureconfig" % "0.17.1",
      "com.typesafe.play" %% "play" % playVersion,
      "com.typesafe.play" %% "routes-compiler" % playVersion,
    )
  )

lazy val plugin = (projectMatrix in file("sbt-plugin"))
  .customRow(
    autoScalaLibrary = false,
    axisValues = Seq(VirtualAxis.jvm),
    _.enablePlugins(BuildInfoPlugin).settings(
      name := "sbt-swagger-play",
      sbtPlugin := true,
      addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.8.17" % Provided),
      buildInfoKeys := Seq[BuildInfoKey](version),
      buildInfoPackage := "com.bokun.sbt",
    )
  )

lazy val runner = (projectMatrix in file("runner"))
  .settings(
    name := "sbt-swagger-play-runner",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.17" % Test,
      "ch.qos.logback" % "logback-classic" % "1.2.10" % Provided,
      "io.swagger.core.v3" % "swagger-core" % swaggerVersion,
      "io.swagger.core.v3" % "swagger-annotations" % swaggerVersion,
      "io.swagger.core.v3" % "swagger-models" % swaggerVersion,
      "io.swagger.core.v3" % "swagger-jaxrs2" % swaggerVersion,
      "javax.ws.rs" % "javax.ws.rs-api" % "2.0.1",
      "com.github.pureconfig" %% "pureconfig" % "0.17.1",
      "com.typesafe.play" %% "play" % playVersion,
      "com.typesafe.play" %% "routes-compiler" % playVersion,
    ),
  ).customRow(
    scalaVersions = Seq(scala212),
    axisValues = Seq(play288, VirtualAxis.jvm),
    _.settings(
      moduleName := "sbt-swagger-play2.8.8-runner",
      libraryDependencies ++= Seq(
        "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.8",
        "org.scalatest" %% "scalatest" % "3.2.17" % Test,
        "ch.qos.logback" % "logback-classic" % "1.2.10" % Provided,
        "io.swagger.core.v3" % "swagger-core" % swaggerVersion,
        "io.swagger.core.v3" % "swagger-annotations" % swaggerVersion,
        "io.swagger.core.v3" % "swagger-models" % swaggerVersion,
        "io.swagger.core.v3" % "swagger-jaxrs2" % swaggerVersion,
        "javax.ws.rs" % "javax.ws.rs-api" % "2.0.1",
        "com.github.pureconfig" %% "pureconfig" % "0.17.1",
        "com.typesafe.play" %% "play" % playVersion,
        "com.typesafe.play" %% "routes-compiler" % playVersion,
      ),
    )
  )

ThisBuild / licenses := Seq(License.MIT)

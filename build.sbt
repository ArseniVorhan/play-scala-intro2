name := """play-scala-intro"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
resolvers += "sorm Scala 2.11 fork" at "http://markusjura.github.io/sorm"

libraryDependencies += "org.sorm-framework" % "sorm" % "0.4.1"

libraryDependencies += "com.h2database" % "h2" % "1.4.187"

dependencyOverrides += "org.scala-lang" % "scala-compiler" % scalaVersion.value
// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

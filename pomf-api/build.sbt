import AssemblyKeys._

assemblySettings

jarName in assembly := "pomf-api.jar"

test in assembly := {}

net.virtualvoid.sbt.graph.Plugin.graphSettings

organization := "com.agourlay"

name := "pomf-api"

version := "0.1"

scalaVersion := "2.10.1"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-feature")

resolvers ++= Seq(
   "sonatype releases"  at "https://oss.sonatype.org/content/repositories/releases/"
  ,"sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
  ,"typesafe release"   at "http://repo.typesafe.com/typesafe/releases/"
  ,"typesafe repo"      at "http://repo.typesafe.com/typesafe/repo/"
  ,"maven central"      at "http://repo1.maven.org/maven2/"
  ,"spray repo"         at "http://repo.spray.io/"
)

libraryDependencies ++= Seq(
   "io.spray"           %   "spray-can"       % "1.1-M7"          withSources()
  ,"io.spray"           %   "spray-routing"   % "1.1-M7"          withSources()
  ,"io.spray"           %   "spray-testkit"   % "1.1-M7"          withSources()
  ,"io.spray"           %   "spray-caching"   % "1.1-M7"          withSources()
  ,"io.spray"           %%  "spray-json"      % "1.2.3"           withSources()
  ,"com.typesafe.akka"  %%  "akka-slf4j"      % "2.1.4"           withSources()
  ,"com.typesafe.akka"  %%  "akka-actor"      % "2.1.4"           withSources()
  ,"net.debasishg"      %%  "redisclient"     % "2.10"            withSources()
  ,"com.github.sstone"  %%  "amqp-client"     % "1.1"             withSources()
  ,"com.typesafe.slick" %%  "slick"           % "1.0.1-RC1"       withSources()
  ,"com.h2database"     %   "h2"              % "1.3.171"       
  ,"postgresql"         %   "postgresql"      % "9.2-1002.jdbc4"
  ,"ch.qos.logback"     %   "logback-classic" % "1.0.13"          withSources()
  ,"org.xerial"         %   "sqlite-jdbc"     % "3.7.2"           withSources()
  ,"junit"              %   "junit"           % "4.11" % "test"   withSources()
  ,"org.specs2"         %%  "specs2"          % "1.14" % "test"   withSources()  
)

seq(Revolver.settings: _*)

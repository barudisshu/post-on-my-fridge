organization := "com.agourlay"

name := "pomf-api"

version := "0.1"

scalaVersion := "2.10.0"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-feature")

resolvers ++= Seq(
  "sonatype releases" at "https://oss.sonatype.org/content/repositories/releases/",
  "sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
  "typesafe repo" at "http://repo.typesafe.com/typesafe/releases/",
  "maven central" at "http://repo1.maven.org/maven2/",
  "spray repo" at "http://repo.spray.io/"
)

libraryDependencies ++= Seq(
  "io.spray"            %   "spray-can"            % "1.1-M7"          withSources()
  ,"io.spray"           %   "spray-routing"        % "1.1-M7"          withSources()
  ,"io.spray"           %   "spray-testkit"        % "1.1-M7"          withSources()
  ,"io.spray"           %   "spray-caching"        % "1.1-M7"          withSources()
  ,"io.spray"           %%  "spray-json"           % "1.2.3"           withSources()
  ,"com.typesafe.akka"  %%  "akka-slf4j"           % "2.1.1"           withSources()
  ,"com.typesafe.akka"  %%  "akka-actor"           % "2.1.1"           withSources()
  ,"net.debasishg"      %%  "redisclient"          % "2.9"             withSources()
  ,"com.github.sstone"  %%  "amqp-client"          % "1.1"             withSources()
  ,"org.specs2"         %%  "specs2"               % "1.14" % "test"   withSources() 
  ,"com.typesafe.slick" %%  "slick"                % "1.0.0"           withSources()
  ,"com.h2database"     %   "h2"                   % "1.3.170"       
  ,"postgresql"         %   "postgresql"           % "9.1-901-1.jdbc4" withSources()
  ,"org.xerial"         %   "sqlite-jdbc"          % "3.7.2"           withSources()
  ,"junit"              %   "junit"                % "4.11" % "test"   withSources() 
  ,"ch.qos.logback"     %   "logback-classic"      % "1.0.9"           withSources()
)

seq(Revolver.settings: _*)
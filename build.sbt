name := "pts4"

version := "1.0"

lazy val `pts4` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq( javaJdbc , ehcache , javaWs )

libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"

// Bootstrap 3
libraryDependencies ++= Seq(
  "com.adrianhurt" %% "play-bootstrap" % "1.2-P26-B4"
)

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.0"

// https://mvnrepository.com/artifact/org.mongodb/mongo-java-driver
libraryDependencies += "org.mongodb" % "mongo-java-driver" % "3.5.0"

// https://mvnrepository.com/artifact/org.jongo/jongo
libraryDependencies += "org.jongo" % "jongo" % "1.3.0"

libraryDependencies += "com.google.code.gson" % "gson" % "2.2.4"

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/testone" )

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

libraryDependencies += "com.typesafe.play" %% "play-iteratees" % "2.6.1"

libraryDependencies += "com.typesafe.play" %% "play-iteratees-reactive-streams" % "2.6.1"

libraryDependencies += guice


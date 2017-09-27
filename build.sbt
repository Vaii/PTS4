name := "pts4"

version := "1.0"

lazy val `pts4` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq( javaJdbc ,  cache , javaWs )

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.0"

// https://mvnrepository.com/artifact/org.mongodb/mongo-java-driver
libraryDependencies += "org.mongodb" % "mongo-java-driver" % "3.5.0"

// https://mvnrepository.com/artifact/org.jongo/jongo
libraryDependencies += "org.jongo" % "jongo" % "1.3.0"

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/testone" )

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

libraryDependencies += "com.typesafe.play" %% "play-iteratees" % "2.6.1"

libraryDependencies += "com.typesafe.play" %% "play-iteratees-reactive-streams" % "2.6.1"

libraryDependencies += guice

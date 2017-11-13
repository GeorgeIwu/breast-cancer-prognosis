import sbt.Keys._

name := "DocumentConsole"

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayJava,PlayEbean)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "org.mindrot" % "jbcrypt" % "0.3m",
  "mysql" % "mysql-connector-java" % "5.1.36",
  "org.apache.commons" % "commons-lang3" % "3.3.2",
  "org.jasypt" % "jasypt" % "1.9.2",
  "org.apache.commons" % "commons-collections4" % "4.0",
  "commons-io" % "commons-io" % "2.3",
  "org.jpos" % "jpos" % "2.0.0",
  "org.apache.httpcomponents" % "httpclient" % "4.4",
  "com.google.inject.extensions" % "guice-assistedinject" % "4.0",
  "it.innove" % "play2-pdf" % "1.3.0",
  "com.ybrikman.ping" %% "big-pipe" % "0.0.13",
  "org.apache.velocity" % "velocity" % "1.7",
  "org.quartz-scheduler" % "quartz" % "2.2.2",
  "org.quartz-scheduler" % "quartz-jobs" % "2.2.2"

)
libraryDependencies +=evolutions



//TwirlKeys.templateFormats ++= Map("stream" -> "bigpipe.scalaapi.bigpipe.HtmlStreamFormat")
//
//TwirlKeys.templateImports ++= Vector("bigpipe.scalaapi.bigpipe.HtmlStream", "bigpipe.scalaapi.bigpipe._")


resolvers ++= Seq(
  "Local Maven Repository" at "file:///"+Path.userHome.absolutePath+"/.m2/repository",
  "Apache" at "https://repo1.maven.org/maven2/",
  "Mvn Cent" at "http://central.maven.org/maven2",
  "jBCrypt Repository" at "http://repo1.maven.org/maven2/org/",
  "Sonatype OSS Snasphots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"
)


resolvers += Resolver.sonatypeRepo("snapshots")


//Use play offline mode. Set up application to stop resolving dependencies
cleanKeepFiles ++= Seq("resolution-cache", "streams").map(target.value / _)

clean := Defaults.doClean(cleanFiles.value, cleanKeepFiles.value ++
  (ivyConfiguration.value match {
    case i: InlineIvyConfiguration => i.resolutionCacheDir
    case _ => None
  }).toList)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

doc in Compile <<= target.map(_ / "none")

offline:=true
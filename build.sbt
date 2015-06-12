import EclipseKeys._

lazy val projectSettings = Seq(
  organization := "com.loopfor.rpn",
  version := "0.1",
  licenses := Seq("Apache License, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))
)

lazy val compilerSettings = Seq(
  scalaVersion := "2.11.6",
  crossPaths := false,
  scalacOptions ++= Seq(
    "-deprecation",
    "-unchecked",
    "-feature",
    "-encoding", "UTF-8"
  ),
  javacOptions ++= Seq(
    "-source", "1.6",
    "-target", "1.6"
  )
)

lazy val dependencySettings = Seq(
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "2.2.4" % "test"
  )
)

lazy val publishSettings = Seq(
  pomIncludeRepository := { _ => false },
  pomExtra :=
    <developers>
      <developer>
        <id>davidledwards</id>
        <name>David Edwards</name>
        <email>david.l.edwards@gmail.com</email>
      </developer>
      <developer>
        <id>brinman2002</id>
        <name>Brandon Inman</name>
        <email>brandonathome@outlook.com</email>
      </developer>
    </developers>
    <build>
    <plugins>
      <plugin>
        <groupId>com.theoryinpractise</groupId>
        <artifactId>clojure-maven-plugin</artifactId>
        <version>1.3.2</version>
        <executions>
          <execution>
            <id>compile-clojure</id>
            <phase>compile</phase>
            <goals>
              <goal>compile</goal>
            </goals>
          </execution>
          <execution>
            <id>test-clojure</id>
            <phase>test</phase>
            <goals>
              <goal>test</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </build>,
  publishTo := Some(
    if (version.value endsWith "SNAPSHOT")
      "Sonatype Nexus Snapshot Repository" at "https://oss.sonatype.org/content/repositories/snapshots/"
    else
      "Sonatype Nexus Release Repository" at "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
  )
)

lazy val eclipseSettings = Seq(
  eclipseOutput in Compile := (classDirectory in Compile).value relativeTo baseDirectory.value map { _.getPath },
  eclipseOutput in Test := (classDirectory in Test).value relativeTo baseDirectory.value map { _.getPath }
)

lazy val rootProject = (project in file(".")).
  settings(
    name := "rpn",
    description := "RPN Compiler and Interpreter"
  ).
  settings(projectSettings: _*).
  settings(compilerSettings: _*).
  settings(dependencySettings: _*).
  settings(publishSettings: _*).
  settings(eclipseSettings: _*).
  settings(packSettings).
  settings(
    packMain := Map("rpnc" -> "com.loopfor.rpn.Compiler",
                    "rpn" -> "com.loopfor.rpn.Interpreter")
  )

seq(clojure.settings :_*)

libraryDependencies += "org.clojure" % "clojure" % "1.5.1"

import sbt.Keys._
import Dependencies._
import Settings._

ThisBuild / scalaVersion := "2.12.15"
ThisBuild / organization := "org.locationtech.geotrellis"
ThisBuild / crossScalaVersions := List("2.12.15", "2.13.6")

lazy val root = Project("geotrellis-geowave", file("."))
  .settings(commonSettings)
  .settings(
    name := "geotrellis-geowave",
    initialize := {
      val curr = VersionNumber(sys.props("java.specification.version"))
      val req = SemanticSelector("=1.8")
      if (!curr.matchesSemVer(req)) {
        val log = Keys.sLog.value
        log.warn(s"Java $req required for GeoTools compatibility. Found Java $curr.\n" +
          "Please change the version of Java running sbt.")
      }
    },
    libraryDependencies ++= Seq(
      newtype,
      java8Compat,
      circe("generic-extras").value,
      circe("json-schema").value,
      geotrellisRaster,
      geotrellisStore,
      geowaveStore,
      geowaveIndex,
      geowaveGeotime,
      geowaveGuava % Test, // tracking geowave guava requirement
      geowaveCassandra % Test,
      geotrellisRasterTestkit % Test,
      scalatest % Test,
      logbackClassic % Test
    ),
    Test / fork := true
  )

lazy val `geowave-benchmark` = (project in file("geowave/benchmark"))
  .dependsOn(root)
  .enablePlugins(JmhPlugin)
  .settings(Seq(
    name := "geotrellis-geowave-benchmark",
    libraryDependencies ++= Seq(
      geowaveGuava, // tracking geowave guava requirement
      geowaveCassandra,
      logbackClassic
    ),
    Test / fork := true
  ) ++ commonSettings)
  .settings(publish / skip := true)
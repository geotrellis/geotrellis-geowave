/*
 * Copyright (c) 2014 Azavea.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import sbt._
import sbt.Keys._

object Dependencies {
  object Version {
    val geotrellis = "3.6.0"
    val cassandra = "3.7.2"
    val geowave = "1.2.0"
  }

  private def ver(for212: String, for213: String) = Def.setting {
    CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, 12)) => for212
      case Some((2, 13)) => for213
      case _             => sys.error("not good")
    }
  }
  def circe(module: String) = Def.setting {
    module match {
      case "json-schema" => "io.circe" %% s"circe-$module" % "0.1.0"
      case _             => "io.circe" %% s"circe-$module" % "0.13.0"
    }
  }

  def scalaReflect(version: String) = "org.scala-lang" % "scala-reflect" % version

  val scalatest = "org.scalatest" %% "scalatest" % "3.2.5"
  val logbackClassic = "ch.qos.logback" % "logback-classic" % "1.2.3"
  val geotrellisRaster = "org.locationtech.geotrellis" %% "geotrellis-raster" % Version.geotrellis
  val geotrellisRasterTestkit = "org.locationtech.geotrellis" %% "geotrellis-raster-testkit" % Version.geotrellis
  val geotrellisStore = "org.locationtech.geotrellis" %% "geotrellis-store" % Version.geotrellis
  val geowaveRaster = "org.locationtech.geowave" % "geowave-adapter-raster" % Version.geowave
  val geowaveVector = "org.locationtech.geowave" % "geowave-adapter-vector" % Version.geowave
  val geowaveIndex = "org.locationtech.geowave" % "geowave-core-index" % Version.geowave
  val geowaveStore = "org.locationtech.geowave" % "geowave-core-store" % Version.geowave
  val geowaveGeotime = "org.locationtech.geowave" % "geowave-core-geotime" % Version.geowave
  val geowaveCassandra = "org.locationtech.geowave" % "geowave-datastore-cassandra" % Version.geowave
  val geowaveGuava = "com.google.guava" % "guava" % "25.1-jre"
  val squants = "org.typelevel" %% "squants" % "1.7.0"
  val newtype = "io.estatico" %% "newtype" % "0.4.4"
  val java8Compat = "org.scala-lang.modules" %% "scala-java8-compat" % "0.9.1"

  // located in the OSGeo repo: https://repo.osgeo.org/repository/release/
  // 'works with' due to license issues
  val jaiCore = "javax.media" % "jai_core" % "1.1.3"
  val jaiCodec = "javax.media" % "jai_codec" % "1.1.3"
  val imageIo = "javax.media" % "jai_imageio" % "1.1"

  val imageioExtUtilities = "it.geosolutions.imageio-ext" % "imageio-ext-utilities" % "1.3.5"

  val worksWithDependencies = Seq(jaiCore, jaiCodec, imageIo, imageioExtUtilities).map(_ % Provided)
}

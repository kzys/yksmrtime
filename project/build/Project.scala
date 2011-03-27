import sbt._

class Project(info: ProjectInfo) extends DefaultProject(info) with conscript.Harness {
  val jlayer = "de.huxhorn.sulky" % "de.huxhorn.sulky.3rdparty.jlayer" % "1.0"
  val scalaTest = "org.scalatest" % "scalatest" % "1.3"
}

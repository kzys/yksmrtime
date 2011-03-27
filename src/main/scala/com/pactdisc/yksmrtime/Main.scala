package com.pactdisc.yksmrtime

import java.util.Calendar
import java.net.URL
import java.io.{File, OutputStream}

case class Exit(val code: Int) extends xsbti.Exit

class Main extends xsbti.AppMain {
  def run(config: xsbti.AppConfiguration) = {
    Main.main(config.arguments)
    Exit(0)
  }
}

object Main {
  val CLOCK_AIF_DIR = "http://yksmrdvd.com/SOUND/clock_aif/"
  def mp3(kind: String, n: Int) = {
    (kind + '/' + kind) + (if (kind == "H") (n % 12) else n) + ".mp3"
  }

  def originalAndCaches(root: File, paths: Iterable[String]) = {
    paths.map { path => {
      new URL(CLOCK_AIF_DIR + path) ->
        new File(root, ".yksmrtime/cache/" + path)
    } }
  }

  def playFile(file: File) = {
    val s = new java.io.FileInputStream(file)
    new javazoom.jl.player.Player(s).play
    s.close
  }

  def cache(root: String, paths: Iterable[String]) = {
    originalAndCaches(new File(root), paths).map { pair => {
      val (source, dest) = pair

      if (! dest.canRead) {
        dest.getParentFile.mkdirs

        val is = source.openStream
        val os = new java.io.FileOutputStream(dest)

        var c = -1
        while ({ c = is.read; c != -1 }) {
          os.write(c)
        }
        os.close
      }

      dest
    } }
  }

  def main(argv: Array[String]): Unit = {
    val now = Calendar.getInstance
    val paths = List(mp3("H", now.get(Calendar.HOUR)),
                     mp3("M", now.get(Calendar.MINUTE)),
                     mp3("S", now.get(Calendar.SECOND)))
    cache(System.getProperty("user.home"), paths) foreach playFile
  }
}

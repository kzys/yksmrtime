import org.scalatest.FunSuite
import com.pactdisc.yksmrtime.Main._
import java.io.File
import java.net.URL

class MainTest extends FunSuite {

  test("mp3") {
    assert(mp3("H", 23) === "H/H11.mp3")
    assert(mp3("M", 23) === "M/M23.mp3")
    assert(mp3("S", 23) === "S/S23.mp3")
  }

  test("path -> (source, cache)") {
    val pairs = originalAndCaches(new File("tmp"), List("H/H11.mp3"))
    assert {
      pairs === List((new URL(CLOCK_AIF_DIR + "H/H11.mp3") ->
                      new File("tmp/.yksmrtime/cache/H/H11.mp3")))
    }
  }
}

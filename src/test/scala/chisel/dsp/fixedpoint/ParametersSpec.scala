// See LICENSE for license details.

package chisel.dsp.fixedpoint

import chisel.dsp.DspException
import org.scalatest.{Matchers, FlatSpec}

import firrtl_interpreter._

//scalastyle:off magic.number

class ParametersSpec extends FlatSpec with Matchers {
  behavior of "Parameter constructor"

  it should "throw exceptions if low exceed high" in {
    intercept[DspException] {
      Parameters(12, 1, 5, 6)
    }
  }
  it should "throw exceptions not enough bits for high or low" in {
    intercept[DspException] {
      Parameters(4, 0, 16, 0)
    }
    intercept[DspException] {
      Parameters(4, 0, -5, -9)
    }
  }

  behavior of "addition"

  it should "have decimal position equal to the max of the two decimal positions" in {
    for {
      nb1 <- 1 to 10
      nb2 <- 1 to 10
      dp1 <- -4 to 4
      dp2 <- -4 to 4
    } {
      val p1 = Parameters(nb1, dp1)
      val p2 = Parameters(nb2, dp2)

      // println(s"parameter addition of $p1 $p2")

      val p3 = p1 + p2

      assert(p3.numberOfBits === nb1.max(nb2)+1, s"number of bits wrong for $p1 $p2")
      assert(p3.decimalPosition === dp1.max(dp2), s"decimal position wrong for $p1 $p2")
    }
  }

  it should "have ranges limit the numberOfBits in a sum" in {
    val p1 = Parameters(8, 1, 2, 1)
    val p2 = Parameters(12, 1, 2, 1)

    val p3 = p1 + p2

    println(s"p3 = $p3")

    for {
      nb1 <- 1 to 10
      nb2 <- 1 to 10
      dp1 <- -4 to 4
      dp2 <- -4 to 4
    } {
      val t1 = Parameters(nb1, dp1)
      val t2 = Parameters(nb2, dp2)




      // println(s"parameter addition of $p1 $p2")

      val p3 = p1 + p2

      assert(p3.numberOfBits === nb1.max(nb2)+1, s"number of bits wrong for $p1 $p2")
      assert(p3.decimalPosition === dp1.max(dp2), s"decimal position wrong for $p1 $p2")
    }
  }

  it should "support negative decimal positions" in {
    val p1 = Parameters(5, -2, 7, 6)
    val p2 = Parameters(1, 0, 0, -1)

    val p3 = p1 + p2

    println(s"p3 = $p3")
  }

  behavior of "multiplication"

  it should "confirm min calculation" in {
  }
}

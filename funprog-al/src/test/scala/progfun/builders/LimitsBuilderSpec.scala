package progfun.builders

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import progfun.types.Limits
import progfun.exceptions.InputFormatException
import scala.util.{Failure, Success}

class LimitsBuilderSpec extends AnyFlatSpec with Matchers {
  "LimitsBuilder" should "successfully parse valid input" in {
    val input = "5 5"
    val result = LimitsBuilder.fromInput(input)
    assert(result == Success(Limits(5, 5)))
  }

  it should "fail to parse invalid input" in {
    val input = "5"
    val result = LimitsBuilder.fromInput(input)
    result match {
      case Failure(_: InputFormatException) => assert(true)
      case _ => fail("Expected an InputFormatException")
    }
  }
}

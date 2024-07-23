package progfun.builders

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import progfun.types.{Location, Position, Trimmer}
import progfun.exceptions.InputFormatException
import scala.util.{Failure, Success}

class TrimmerBuilderSpec extends AnyFlatSpec with Matchers {
  "TrimmerBuilder" should "successfully parse valid input" in {
    val input = List("1 2 N", "GAGAGAGAA")
    val result = TrimmerBuilder.fromInput(input)
    result match {
      case Success(trimmers) =>
        assert(
          trimmers.contains(
            Trimmer(
              Position(Location(1, 2), "N"),
              List("G", "A", "G", "A", "G", "A", "G", "A", "A")
            )
          )
        )
      case Failure(_) => fail("Expected successful parsing")
    }
  }

  it should "fail to parse invalid position" in {
    val input = List("1 2", "GAGAGAGAA")
    val result = TrimmerBuilder.fromInput(input)
    result match {
      case Failure(_: InputFormatException) => assert(true)
      case _ => fail("Expected an InputFormatException")
    }
  }

  it should "fail to parse invalid instructions" in {
    val input = List("1 2 N", "GAXGAGAGAA")
    val result = TrimmerBuilder.fromInput(input)
    result match {
      case Failure(_: InputFormatException) => assert(true)
      case _ => fail("Expected an InputFormatException")
    }
  }
}

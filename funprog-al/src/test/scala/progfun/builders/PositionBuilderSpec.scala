package progfun.builders

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import progfun.types.{Location, Position}
import progfun.exceptions.GardeningEngineException
import scala.util.{Failure, Success}

class PositionBuilderSpec extends AnyFlatSpec with Matchers {
  "PositionBuilder" should "move forward correctly" in {
    val position = Position(Location(1, 2), "N")
    val result = PositionBuilder.fromInstruction("A", position)
    assert(result == Success(Position(Location(1, 3), "N")))
  }

  it should "turn left correctly" in {
    val position = Position(Location(1, 2), "N")
    val result = PositionBuilder.fromInstruction("G", position)
    assert(result == Success(Position(Location(1, 2), "W")))
  }

  it should "turn right correctly" in {
    val position = Position(Location(1, 2), "N")
    val result = PositionBuilder.fromInstruction("D", position)
    assert(result == Success(Position(Location(1, 2), "E")))
  }

  it should "fail on invalid instruction" in {
    val position = Position(Location(1, 2), "N")
    val result = PositionBuilder.fromInstruction("X", position)
    result match {
      case Failure(_: GardeningEngineException) => assert(true)
      case _ => fail("Expected a GardeningEngineException")
    }
  }
}

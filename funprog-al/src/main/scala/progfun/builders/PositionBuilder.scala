package progfun.builders

import scala.util.Try
import progfun.types.{Location, Position}
import progfun.exceptions.GardeningEngineException
import scala.util.Success
import scala.util.Failure

object PositionBuilder {
  def fromInstruction(instruction: String, position: Position): Try[Position] =
    instruction match {
      case "A" => moveForward(position.point, position.direction)
      case "G" => turnLeft(position.point, position.direction)
      case "D" => turnRight(position.point, position.direction)
      case _ =>
        Failure(
          new GardeningEngineException(
            "Trimmer instruction should be A, G or D"
          )
        )
    }

  private val wrongDirectionException = Failure(
    new GardeningEngineException("Trimmer direction should be N, S, W or E")
  )
  private def moveForward(point: Location, direction: String): Try[Position] =
    direction match {
      case "N" => Success(Position(Location(point.x, point.y + 1), direction))
      case "S" => Success(Position(Location(point.x, point.y - 1), direction))
      case "W" => Success(Position(Location(point.x - 1, point.y), direction))
      case "E" => Success(Position(Location(point.x + 1, point.y), direction))
      case _   => wrongDirectionException
    }

  private def turnLeft(point: Location, direction: String): Try[Position] =
    direction match {
      case "N" => Success(Position(point, "W"))
      case "S" => Success(Position(point, "E"))
      case "W" => Success(Position(point, "S"))
      case "E" => Success(Position(point, "N"))
      case _   => wrongDirectionException
    }

  private def turnRight(point: Location, direction: String): Try[Position] =
    direction match {
      case "N" => Success(Position(point, "E"))
      case "S" => Success(Position(point, "W"))
      case "W" => Success(Position(point, "N"))
      case "E" => Success(Position(point, "S"))
      case _   => wrongDirectionException
    }
}

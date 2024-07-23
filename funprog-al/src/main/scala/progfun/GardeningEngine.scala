package progfun

import progfun.types.{
  Instructions,
  Limits,
  Location,
  Position,
  ProcessedTrimmer,
  Result,
  Trimmer
}
import scala.util.{Failure, Success, Try}
import progfun.exceptions.GardeningEngineException
import progfun.builders.{PositionBuilder, TrimmerBuilder}
import scala.annotation.tailrec

object GardeningEngine {
  def execute(instructions: Instructions): Try[Result] =
    processTrimmers(instructions.limite, instructions.tondeuses, List()) match {
      case Failure(exception) => Failure(exception)
      case Success(processedTrimmers) =>
        Success(Result(instructions.limite, processedTrimmers))
    }

  private def processTrimmers(
      limits: Limits,
      trimmersToProcess: List[Trimmer],
      processedPositions: List[ProcessedTrimmer]): Try[List[ProcessedTrimmer]] =
    trimmersToProcess match {
      case Nil => Success(processedPositions)
      case head :: next => {
        val prohibitedLocations =
          next.map(_.debut.point) ++ processedPositions.map(_.fin.point)
        getTrimmerEndPosition(
          limits,
          head.debut,
          prohibitedLocations,
          head.instructions
        ) match {
          case Failure(exception) => Failure(exception)
          case Success(value) =>
            processTrimmers(
              limits,
              next,
              processedPositions ++ List(TrimmerBuilder.process(head, value))
            )
        }
      }
    }

  @tailrec()
  def getTrimmerEndPosition(
      limits: Limits,
      position: Position,
      prohibitedLocations: List[Location],
      instructions: List[String]): Try[Position] =
    instructions match {
      case Nil => getPositionIfInbound(limits, position)
      case head :: next =>
        PositionBuilder.fromInstruction(head, position) match {
          case Failure(exception) => Failure(exception)
          case Success(newPosition) =>
            getPositionIfNotColliding(newPosition, prohibitedLocations) match {
              case Success(_) =>
                getTrimmerEndPosition(
                  limits,
                  newPosition,
                  prohibitedLocations,
                  next
                )
              case Failure(collisionException) => Failure(collisionException)
            }
        }
    }

  private def getPositionIfInbound(
      limits: Limits,
      position: Position): Try[Position] =
    position match {
      case Position(location, _)
          if location.x < 0 || location.x > limits.x || location.y < 0 || location.y > limits.y =>
        Failure(
          new GardeningEngineException("A trimmer is beyond garden bound")
        )
      case location => Success(location)
    }

  private def getPositionIfNotColliding(
      position: Position,
      prohibitedLocations: List[Location]): Try[Position] =
    prohibitedLocations.find(location =>
      location.x == position.point.x && location.y == position.point.y
    ) match {
      case None => Success(position)
      case Some(_) =>
        Failure(
          new GardeningEngineException("There is another trimmer on the way")
        )
    }
}

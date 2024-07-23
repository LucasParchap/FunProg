package progfun.builders

import progfun.exceptions.InputFormatException
import progfun.types.{Location, Position, ProcessedTrimmer, Trimmer}
import scala.util.{Failure, Success, Try}

object TrimmerBuilder {
  def process(trimmer: Trimmer, endPosition: Position): ProcessedTrimmer =
    ProcessedTrimmer(trimmer.debut, trimmer.instructions, endPosition)

  def fromInput(input: List[String]): Try[List[Trimmer]] =
    input.grouped(2).toList match {
      case inputs if inputs.length > 0 => parseInputs(inputs)
      case _ =>
        Failure(
          new InputFormatException(
            "Your input file must contain at least 1 trimmer declaration"
          )
        )
    }

  private def parseInputs(inputs: List[List[String]]): Try[List[Trimmer]] =
    generateTrimmers(inputs) match {
      case trimmers if trimmers.forall(_.isSuccess) =>
        Success(trimmers.collect { case Success(trim) => trim })
      case trimmers => handleTrimmerListWithErrors(trimmers)
    }

  private def handleTrimmerListWithErrors(
      trimmers: List[Try[Trimmer]]): Try[List[Trimmer]] =
    trimmers.find(_.isFailure) match {
      case Some(Failure(value)) => Failure(value)
      case _                    => Success(List[Trimmer]())
    }

  private def generateTrimmers(inputs: List[List[String]]): List[Try[Trimmer]] =
    inputs.map {
      case List(firstLine, secondLine) =>
        for {
          position     <- parseLineAsPosition(firstLine)
          instructions <- parseLineAsInstructions(secondLine)
        } yield Trimmer(position, instructions)
      case _ =>
        Failure(
          new InputFormatException(
            "Trimmer configuration have to always be two lines"
          )
        )
    }

  private def parseLineAsPosition(input: String): Try[Position] =
    input.split(" ") match {
      case Array(x, y, d) if d == "N" || d == "E" || d == "S" || d == "W" =>
        Success(Position(Location(x.toInt, y.toInt), d))
      case _ =>
        Failure(
          new InputFormatException(
            "Trimmer Position must use this format : x y d"
          )
        )
    }

  private def parseLineAsInstructions(input: String): Try[List[String]] =
    input.split("") match {
      case instructions
          if instructions.forall(c => c == "D" || c == "G" || c == "A") =>
        Success(instructions.toList)
      case _ =>
        Failure(
          new InputFormatException("Trimmer instructions must be D, G or A")
        )
    }
}

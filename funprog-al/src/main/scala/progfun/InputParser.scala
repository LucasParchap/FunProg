package progfun

import com.typesafe.config.ConfigFactory
import better.files._
import progfun.exceptions.InputFormatException
import progfun.builders.LimitsBuilder
import progfun.types.{Instructions, Limits, Trimmer}
import progfun.builders.TrimmerBuilder
import scala.util.{Failure, Try}

object InputParser {
  def execute() = {
    val inputLocation = getInputFileLocation()
    val input = getInputInstructions(inputLocation)
    parseInputsIntoInstructions(input)
  }

  private def getInputFileLocation(): String = {
    val globalConf = ConfigFactory.load()
    globalConf.getString("application.input-file")
  }

  private def getInputInstructions(inputLocation: String): List[String] = File(
    inputLocation
  ).lines.toList

  private def parseInputsIntoInstructions(
      input: List[String]): Try[Instructions] = for {
    limits   <- getLimitsFromInput(input)
    trimmers <- getTrimmersFromInput(input)
  } yield Instructions(limits, trimmers)

  private def getLimitsFromInput(input: List[String]): Try[Limits] =
    input.headOption match {
      case Some(value) => LimitsBuilder.fromInput(value)
      case None =>
        Failure(
          new InputFormatException(
            "Input file must start with 2 int indicating the garden size with this format 'x y'"
          )
        )
    }

  private def getTrimmersFromInput(input: List[String]): Try[List[Trimmer]] =
    input match {
      case _ :: tail => TrimmerBuilder.fromInput(tail)
      case _ =>
        Failure(
          new InputFormatException(
            "Input file must contain at least 1 trimmer configuration"
          )
        )
    }
}

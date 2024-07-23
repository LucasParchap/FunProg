package progfun.builders

import progfun.types.Limits
import progfun.exceptions.InputFormatException
import scala.util.{Failure, Success, Try}

object LimitsBuilder {
  def fromInput(input: String): Try[Limits] =
    input.split(" ").toList match {
      case first :: second :: _ => Success(Limits(first.toInt, second.toInt))
      case _ =>
        Failure(
          new InputFormatException(
            "Input file must start with 2 int indicating the garden size with this format 'x y'"
          )
        )
    }
}

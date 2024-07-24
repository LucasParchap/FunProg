package progfun


import scala.util.{Failure, Success, Try}
// import scala.util.{Failure, Success, Try}

object Main extends App {
  def execute(): Try[String] = {
    InputParser.execute() match {
      case Success(instructions) =>
        GardeningEngine.execute(instructions) match {
          case Failure(exception) => Failure(exception)
          case Success(value)     => OutputEngine.execute(value)
        }
      case Failure(exception) => {
        Failure(exception)
      }
    }
  }

  execute() match {
    case Success(value)     => print(value)
    case Failure(exception) => print(exception)
  }
}

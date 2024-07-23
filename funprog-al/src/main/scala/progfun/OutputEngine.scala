package progfun

import progfun.types.Result
import progfun.exceptions.OutputEngineException
import scala.util.{Failure, Success, Try}
import com.typesafe.config.ConfigFactory
import better.files.File

object OutputEngine {
  def execute(result: Result): Try[String] = for {
    jsonExport <- exportResult(result.toJson(0), "json")
    yamlExport <- exportResult(result.toYaml(0), "yaml")
    csvExport  <- exportResult(result.toCsv(), "csv")
  } yield f"Output informations:\n$jsonExport\n$yamlExport\n$csvExport"

  def exportResult(result: String, extension: String): Try[String] =
    getExportLocation(extension) match {
      case Failure(exception) => Failure(exception)
      case Success(fileLocation) => {
        val file = File(fileLocation)
        Try(file.createFileIfNotExists().write(result)) match {
          case Failure(_) =>
            Failure(
              new OutputEngineException(
                f"Impossible to export result in .$fileLocation please make sure you have the writing permission on file destination"
              )
            )
          case Success(_) =>
            Success(
              f"File $fileLocation contain result to format ${extension.toUpperCase()}"
            )
        }
      }
    }

  private def getExportLocation(fileExtension: String): Try[String] = {
    val configuration = ConfigFactory.load()
    Try(
      configuration.getString(f"application.output-$fileExtension-file")
    ) match {
      case Success(value) => Success(value)
      case Failure(_) =>
        Failure(
          new OutputEngineException(
            f"Impossible to find a value for .$fileExtension file make sure you have application.output-$fileExtension-file in .conf"
          )
        )
    }
  }
}

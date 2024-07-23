package progfun.exceptions

case class CustomException(errorMessage: String) extends Throwable {
  val exceptionType: String = "Exception"

  override def toString(): String = f"${this.exceptionType} : ${errorMessage}"
}

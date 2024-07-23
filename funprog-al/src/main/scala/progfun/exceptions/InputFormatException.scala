package progfun.exceptions

class InputFormatException(exceptionMessage: String)
    extends CustomException(exceptionMessage) {
  override val exceptionType: String = "Wrong Input File Format Exception"
}

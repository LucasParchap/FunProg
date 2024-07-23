package progfun.exceptions

class OutputEngineException(exceptionMessage: String)
    extends CustomException(exceptionMessage) {
  override val exceptionType: String = "Output Engine Exception"
}

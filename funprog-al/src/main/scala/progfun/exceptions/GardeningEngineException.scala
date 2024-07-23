package progfun.exceptions

class GardeningEngineException(exceptionMessage: String)
    extends CustomException(exceptionMessage) {
  override val exceptionType: String = "Gardening Engine Exception"
}

package progfun

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import progfun.types.{
  Instructions,
  Limits,
  Location,
  Position,
  ProcessedTrimmer,
  Trimmer
}

import scala.util.{Failure, Success}

class GardeningEngineSpec extends AnyFlatSpec with Matchers {
  "GardeningEngine" should "execute instructions correctly" in {
    val limits = Limits(5, 5)
    val trimmer1 = Trimmer(
      Position(Location(1, 2), "N"),
      List("G", "A", "G", "A", "G", "A", "G", "A", "A")
    )
    val trimmer2 = Trimmer(
      Position(Location(3, 3), "E"),
      List("A", "A", "D", "A", "A", "D", "A", "D", "D", "A")
    )
    val instructions = Instructions(limits, List(trimmer1, trimmer2))

    val result = GardeningEngine.execute(instructions)
    result match {
      case Success(processedTrimmers) =>
        assertFirstTrimmer(processedTrimmers.tondeuses.headOption)
        assertSecondTrimmer(processedTrimmers.tondeuses.lift(1))
      case Failure(_) => fail("Expected successful execution")
    }
  }

  it should "fail if a trimmer goes out of bounds" in {
    val limits = Limits(5, 5)
    val trimmer =
      Trimmer(Position(Location(1, 2), "N"), List("A", "A", "A", "A", "A", "A"))
    val instructions = Instructions(limits, List(trimmer))

    val result = GardeningEngine.execute(instructions)
    result match {
      case Failure(_) => assert(true)
      case Success(_) => fail("Expected failure due to out of bounds")
    }
  }

  private def assertFirstTrimmer(option: Option[ProcessedTrimmer]): Unit = {
    option match {
      case Some(firstTrimmer) =>
        assert(firstTrimmer.fin == Position(Location(1, 3), "N"))
        ()
      case None => fail("Expected at least one trimmer")
    }
  }

  private def assertSecondTrimmer(option: Option[ProcessedTrimmer]): Unit = {
    option match {
      case Some(secondTrimmer) =>
        assert(secondTrimmer.fin == Position(Location(5, 1), "E"))
        ()
      case None => fail("Expected a second trimmer")
    }
  }
}

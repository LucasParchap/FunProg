package progfun.types

case class Result(limite: Limits, tondeuses: List[ProcessedTrimmer]) {
  def toJson(indent: Int) = f"{\n" +
    f"${"    " * (indent + 1)}\"limite\": ${limite.toJson(indent + 1)},\n" +
    f"${"    " * (indent + 1)}\"tondeuses\": [\n" +
    f"${"    " * (indent + 2)}${tondeuses
        .map(tondeuse => tondeuse.toJson(indent + 2))
        .mkString(f",\n${"    " * (indent + 2)}")}\n" +
    f"${"    " * (indent + 1)}]\n" +
    f"${"    " * indent}}"

  def toYaml(indent: Int) = f"limite: ${limite.toYaml(indent + 1)}\n" +
    f"tondeuses:\n" +
    f"${tondeuses
        .map(tondeuse => f"${"  " * (indent + 1)}- ${tondeuse.toYaml(indent + 1)}")
        .mkString("\n")}"

  def toCsv() =
    f"numéro;début_x;début_y;début_direction;fin_x;fin_y;fin_direction;instructions\n" +
      tondeuses.zipWithIndex
        .map { case (tondeuse, index) => f"${index + 1};" + tondeuse.toCsv() }
        .mkString("\n")
}

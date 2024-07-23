package progfun.types

case class Location(x: Int, y: Int) {
  def toJson(indent: Int) = f"{\n" +
    f"${"    " * (indent + 1)}\"x\": $x,\n" +
    f"${"    " * (indent + 1)}\"y\": $y\n" +
    f"${"    " * indent}}"

  def toYaml(indent: Int) = f"\n" +
    f"${"  " * indent}x: $x\n" +
    f"${"  " * indent}y: $y"

  def toCsv() = f"$x;$y"
}

case class Position(point: Location, direction: String) {
  def toJson(indent: Int) = f"{\n" +
    f"${"    " * (indent + 1)}\"point\": ${point.toJson(indent + 1)},\n" +
    f"${"    " * (indent + 1)}\"direction\": \"$direction\"\n" +
    f"${"    " * indent}}"

  def toYaml(indent: Int) = f"\n" +
    f"${"  " * indent}point: ${point.toYaml(indent + 1)}\n" +
    f"${"  " * indent}direction: $direction"

  def toCsv() = f"${point.toCsv()};$direction"
}

case class Trimmer(debut: Position, instructions: List[String])

case class ProcessedTrimmer(
    debut: Position,
    instructions: List[String],
    fin: Position) {
  def toJson(indent: Int) = f"{\n" +
    f"${"    " * (indent + 1)}\"debut\": ${debut.toJson(indent + 1)},\n" +
    f"${"    " * (indent + 1)}\"direction\": [${instructions.map(char => f"\"$char\"").mkString(",")}],\n" +
    f"${"    " * (indent + 1)}\"fin\": ${fin.toJson(indent + 1)}\n" +
    f"${"    " * indent}}"

  def toYaml(indent: Int) =
    f"debut: ${debut.toYaml(indent + 2)}\n" +
      f"${"  " * (indent + 1)}instructions:\n" +
      instructions
        .map(instruction => f"${"  " * (indent + 2)}- $instruction")
        .mkString("\n") + "\n" +
      f"${"  " * (indent + 1)}fin: ${fin.toYaml(indent + 2)}"

  def toCsv() = f"${debut.toCsv()};${fin.toCsv()};${instructions.mkString("")}"
}

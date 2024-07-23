package progfun.types

case class Limits(x: Int, y: Int) {
  def toJson(indent: Int) = f"{\n" +
    f"${"    " * (indent + 1)}\"x\": $x,\n" +
    f"${"    " * (indent + 1)}\"y\": $y\n" +
    f"${"    " * indent}}"

  def toYaml(indent: Int) = f"\n" +
    f"${"  " * indent}x: $x\n" +
    f"${"  " * indent}y: $y"
}

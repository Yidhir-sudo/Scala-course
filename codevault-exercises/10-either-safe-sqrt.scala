object Main extends App {
  println(safeSqrt(9))
  println(safeSqrt(-4))

  def safeSqrt(x: Double): Either[String, Double] =
    if (x < 0) Left("Negative number") else Right(math.sqrt(x))
}

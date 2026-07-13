// Reference solution — Either: Safe Square Root — teacher-only, do not share with students

object EitherSafeSqrt {

  def main(args: Array[String]): Unit = {
    println(safeSqrt(9))
    println(safeSqrt(-4))
  }

  def safeSqrt(x: Double): Either[String, Double] =
    if (x < 0) Left("Negative number")
    else Right(math.sqrt(x))
}

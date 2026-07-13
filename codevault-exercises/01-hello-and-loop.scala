// Reference solution — Hello and Loop — teacher-only, do not share with students

object Hello {
  def main(args: Array[String]): Unit = {
    val name: String = "Ada"
    println(s"Hello, $name!")

    for (i <- 1 to 10) println(i)
  }
}

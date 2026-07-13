object Main extends App {
  val text = "Scala is great and Scala is functional"
  textPipeline(text).toList.sortBy(_._1).foreach { case (word, count) => println(s"$word -> $count") }

  def textPipeline(text: String): Map[String, Int] =
    text.split("\\s+").map(_.toLowerCase).filter(_.length >= 4)
      .groupBy(identity).map { case (word, occurrences) => word -> occurrences.length }.toMap
}

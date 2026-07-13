// Reference solution — Word Counting — teacher-only, do not share with students

object WordCounting {

  def main(args: Array[String]): Unit = {
    val words = List("A", "B", "A", "C", "B", "A")

    wordCount(words).toList.sortBy(_._1).foreach { case (word, count) =>
      println(s"$word -> $count")
    }
  }

  def wordCount(words: List[String]): Map[String, Int] =
    words.groupBy(identity).view.mapValues(_.size).toMap
}

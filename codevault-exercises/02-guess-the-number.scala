object Main extends App {

  val secret = 42
  val guesses = List(50, 25, 40, 45, 42)

  play(secret, guesses).foreach(println)

  def play(secret: Int, guesses: List[Int]): List[String] = {
    val indexed = guesses.zipWithIndex
    val winIndex = indexed.indexWhere { case (guess, _) => guess == secret }
    val relevant = if (winIndex == -1) indexed else indexed.take(winIndex + 1)

    relevant.map { case (guess, idx) =>
      val attempt = idx + 1
      if (guess < secret) s"Attempt $attempt: $guess is too small!"
      else if (guess > secret) s"Attempt $attempt: $guess is too big!"
      else s"Attempt $attempt: $guess is correct! Found in $attempt attempt(s)."
    }
  }
}

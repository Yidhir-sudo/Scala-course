# 02 — Guess the Number (non-interactive)

> Adapted from [`sessions/session1/Session1Exercise2.scala`](../src/main/scala/sessions/session1/Session1Exercise2.scala) in this repo.

**⚠️ Platform adaptation:** the original exercise reads guesses interactively
via `scala.io.StdIn.readLine()`. CodeVault's `/api/execute` endpoint only
sends `{code, language}` to the sandbox — there is no way to feed stdin to a
running submission, so an interactive version would just hang or throw on
end-of-input. This version replaces the human player with a **fixed list of
guesses** passed as a parameter, turning the exercise into a pure function
that always produces the same, gradable output while still exercising loops,
conditionals, and early exit.

## CodeVault exam fields

| Field | Value |
|---|---|
| Title | Guess the Number |
| Language | `scala` |
| Exam type | `code` |
| Suggested duration | 20 minutes |

### Description

```markdown
You're going to replay a guessing game from a transcript instead of playing
it live: the "player" already made their guesses, in order, and you need to
write the referee logic that reports back on each one.

Write a function:

def play(secret: Int, guesses: List[Int]): List[String]

For each guess, in order, produce one line of feedback:
- "Attempt N: X is too small!"   if X < secret
- "Attempt N: X is too big!"     if X > secret
- "Attempt N: X is correct! Found in N attempt(s)." if X == secret

where N is the 1-based attempt number. Stop as soon as the secret is found —
ignore any guesses that come after it in the list.

**Example**

    val secret = 42
    val guesses = List(50, 25, 40, 45, 42)
    play(secret, guesses).foreach(println)

    // Attempt 1: 50 is too big!
    // Attempt 2: 25 is too small!
    // Attempt 3: 40 is too small!
    // Attempt 4: 45 is too big!
    // Attempt 5: 42 is correct! Found in 5 attempt(s).

Your `main` should call `play` with `secret = 42` and
`guesses = List(50, 25, 40, 45, 42)` and print each line.
```

### Starter code

```scala
object GuessTheNumber {

  def main(args: Array[String]): Unit = {
    val secret = 42
    val guesses = List(50, 25, 40, 45, 42)

    play(secret, guesses).foreach(println)
  }

  def play(secret: Int, guesses: List[Int]): List[String] = {
    // TODO: for each guess (1-based attempt number), build the feedback
    // line, and stop right after the correct guess.
    ???
  }
}
```

### Reference solution (teacher-only — do not share with students)

```scala
object GuessTheNumber {

  def main(args: Array[String]): Unit = {
    val secret = 42
    val guesses = List(50, 25, 40, 45, 42)

    play(secret, guesses).foreach(println)
  }

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
```

### Expected output (for grading)

```text
Attempt 1: 50 is too big!
Attempt 2: 25 is too small!
Attempt 3: 40 is too small!
Attempt 4: 45 is too big!
Attempt 5: 42 is correct! Found in 5 attempt(s).
```

Verified locally with `scala run 02.scala --server=false` (Scala 3) — output
matches exactly.

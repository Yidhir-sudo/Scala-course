# 02 — Guess the Number (non-interactive)

> Adapted from [`sessions/session1/Session1Exercise2.scala`](../src/main/scala/sessions/session1/Session1Exercise2.scala) in this repo.

**⚠️ Platform adaptation:** the original exercise reads guesses interactively
via `scala.io.StdIn.readLine()`. CodeVault's code sandbox never forwards
stdin to a running submission, so an interactive version would just hang or
throw on end-of-input. This version replaces the human player with a
**fixed list of guesses** passed as a parameter, turning the exercise into a
pure function that always produces the same, testable output while still
exercising loops, conditionals, and early exit.

## CodeVault exercise fields

| Field | Value |
|---|---|
| Title | Guess the Number |
| Exercise type | `code` |
| Language | `scala` |
| Course / Training | attach to exactly one — whichever holds session 1 |

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

Call `play` with `secret = 42` and `guesses = List(50, 25, 40, 45, 42)` and
print each line.
```

### Starter code

```scala
object Main extends App {

  val secret = 42
  val guesses = List(50, 25, 40, 45, 42)

  play(secret, guesses).foreach(println)

  def play(secret: Int, guesses: List[Int]): List[String] = {
    // TODO: for each guess (1-based attempt number), build the feedback
    // line, and stop right after the correct guess.
    ???
  }
}
```

### Correction

Teacher-only — do not share with students. Upload [`02-guess-the-number.scala`](02-guess-the-number.scala) via the "Correction" file picker (must be a `.scala` file).

### Test cases

| Name | Call expression | Expected output | Trim | Tolerance |
|---|---|---|---|---|
| guesses converge to the secret | `play(42, List(50, 25, 40, 45, 42)).mkString("\|")` | `Attempt 1: 50 is too big!\|Attempt 2: 25 is too small!\|Attempt 3: 40 is too small!\|Attempt 4: 45 is too big!\|Attempt 5: 42 is correct! Found in 5 attempt(s).` | off | — |

Verified locally by simulating how CodeVault's automated test-case check
evaluates this call expression against the correction — the output matches
the expected output above byte for byte.

# 06 — Functional Pipeline: Text

> Adapted from [`sessions/session3/Session3.scala`](../src/main/scala/sessions/session3/Session3.scala) (Exercise 2) in this repo.

**Test-simplification note:** as with [03 — Word Counting](03-word-counting.md),
the output (and the test case's call expression) sorts the resulting map
alphabetically rather than relying on default `Map` iteration order, so the
comparison is deterministic.

## CodeVault exercise fields

| Field | Value |
|---|---|
| Title | Functional Pipeline: Text |
| Exercise type | `code` |
| Language | `scala` |
| Course / Training | attach to exactly one — whichever holds session 3 |

### Description

```markdown
Write a function:

def textPipeline(text: String): Map[String, Int]

It should, in order:
1. Split the text into words (on whitespace).
2. Lowercase each word.
3. Keep only words with length ≥ 4.
4. Count how many times each remaining word occurs.

**Example**

    val text = "Scala is great and Scala is functional"
    textPipeline(text)
    // scala -> 2, great -> 1, functional -> 1
    // ("is" and "and" are dropped: they're shorter than 4 characters)

Call `textPipeline` on that example text, then print one line per word in
**alphabetical order**, formatted as `word -> count`.
```

### Starter code

```scala
object Main extends App {

  val text = "Scala is great and Scala is functional"
  textPipeline(text).toList.sortBy(_._1).foreach { case (word, count) =>
    println(s"$word -> $count")
  }

  def textPipeline(text: String): Map[String, Int] = {
    // TODO: split -> lowercase -> filter by length -> count occurrences
    ???
  }
}
```

### Correction

Teacher-only — do not share with students. Upload [`06-functional-pipeline-text.scala`](06-functional-pipeline-text.scala) via the "Correction" file picker (must be a `.scala` file).

### Test cases

| Name | Call expression | Expected output | Trim | Tolerance |
|---|---|---|---|---|
| counts words of length >= 4, sorted by key | `textPipeline("Scala is great and Scala is functional").toList.sortBy(_._1).mkString(",")` | `(functional,1),(great,1),(scala,2)` | off | — |

Verified locally by simulating how CodeVault's automated test-case check evaluates a call expression against the correction and the output matches the expected output above.

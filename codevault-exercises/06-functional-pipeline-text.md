# 06 — Functional Pipeline: Text

> Adapted from [`sessions/session3/Session3.scala`](../src/main/scala/sessions/session3/Session3.scala) (Exercise 2) in this repo.

**Test-simplification note:** as with [03 — Word Counting](03-word-counting.md),
`main` prints the resulting map sorted alphabetically rather than relying on
default `Map` iteration order, so the output is deterministic and gradable.

## CodeVault exam fields

| Field | Value |
|---|---|
| Title | Functional Pipeline: Text |
| Language | `scala` |
| Exam type | `code` |
| Suggested duration | 15 minutes |

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

Your `main` should call `textPipeline` on that example text, then print one
line per word in **alphabetical order**, formatted as `word -> count`.
```

### Starter code

```scala
object FunctionalPipelineText {

  def main(args: Array[String]): Unit = {
    val text = "Scala is great and Scala is functional"
    textPipeline(text).toList.sortBy(_._1).foreach { case (word, count) =>
      println(s"$word -> $count")
    }
  }

  def textPipeline(text: String): Map[String, Int] = {
    // TODO: split -> lowercase -> filter by length -> count occurrences
    ???
  }
}
```

### Reference solution (teacher-only — do not share with students)

```scala
object FunctionalPipelineText {

  def main(args: Array[String]): Unit = {
    val text = "Scala is great and Scala is functional"
    textPipeline(text).toList.sortBy(_._1).foreach { case (word, count) =>
      println(s"$word -> $count")
    }
  }

  def textPipeline(text: String): Map[String, Int] =
    text
      .split("\\s+")
      .map(_.toLowerCase)
      .filter(_.length >= 4)
      .groupBy(identity)
      .map { case (word, occurrences) => word -> occurrences.length }
      .toMap
}
```

### Expected output (for grading)

```text
functional -> 1
great -> 1
scala -> 2
```

Verified locally with `scala run 06.scala --server=false` (Scala 3) — output
matches exactly.

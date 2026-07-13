# 03 — Word Counting

> Adapted from [`sessions/session2/Session2Exercise1.scala`](../src/main/scala/sessions/session2/Session2Exercise1.scala) in this repo.

**Simplification note:** the source file shows the *same* result computed
three different ways (`groupBy`, pattern-matched `map`, `foldLeft`) side by
side — great for a lecture, confusing as a single exercise. This version
asks for just one clean implementation. It also prints the map as sorted
`word -> count` lines instead of relying on `Map.toString`, whose entry
order isn't guaranteed — that keeps grading deterministic.

## CodeVault exam fields

| Field | Value |
|---|---|
| Title | Word Counting |
| Language | `scala` |
| Exam type | `code` |
| Suggested duration | 15 minutes |

### Description

```markdown
Write a function:

def wordCount(words: List[String]): Map[String, Int]

It returns a map from each distinct word to the number of times it appears
in the list.

**Hint:** `groupBy` clusters equal elements together; from there you just
need the size of each group.

**Example**

    val words = List("A", "B", "A", "C", "B", "A")
    wordCount(words) // Map(A -> 3, B -> 2, C -> 1)

Your `main` should call `wordCount` on that example list, then print one
line per word in **alphabetical order**, formatted as `word -> count`
(sorting keeps the output predictable — a `Map`'s own printed order isn't
guaranteed).
```

### Starter code

```scala
object WordCounting {

  def main(args: Array[String]): Unit = {
    val words = List("A", "B", "A", "C", "B", "A")

    wordCount(words).toList.sortBy(_._1).foreach { case (word, count) =>
      println(s"$word -> $count")
    }
  }

  def wordCount(words: List[String]): Map[String, Int] = {
    // TODO: group equal words together, then count each group's size
    ???
  }
}
```

### Reference solution

Teacher-only — do not share with students. See [`03-word-counting.scala`](03-word-counting.scala).

### Expected output (for grading)

```text
A -> 3
B -> 2
C -> 1
```

Verified locally with `scala run 03.scala --server=false` (Scala 3) — output
matches exactly.

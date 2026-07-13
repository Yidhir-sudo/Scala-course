# 03 — Word Counting

> Adapted from [`sessions/session2/Session2Exercise1.scala`](../src/main/scala/sessions/session2/Session2Exercise1.scala) in this repo.

**Simplification note:** the source file shows the *same* result computed
three different ways (`groupBy`, pattern-matched `map`, `foldLeft`) side by
side — great for a lecture, confusing as a single exercise. This version
asks for just one clean implementation. It also prints the map as sorted
`word -> count` lines instead of relying on `Map.toString`, whose entry
order isn't guaranteed — that keeps the printed output (and any test case
built on it) deterministic.

## CodeVault exercise fields

| Field | Value |
|---|---|
| Title | Word Counting |
| Exercise type | `code` |
| Language | `scala` |
| Course / Training | attach to exactly one — whichever holds session 2 |

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

Call `wordCount` on that example list, then print one line per word in
**alphabetical order**, formatted as `word -> count` (sorting keeps the
output predictable — a `Map`'s own printed order isn't guaranteed).
```

### Starter code

```scala
object Main extends App {

  val words = List("A", "B", "A", "C", "B", "A")

  wordCount(words).toList.sortBy(_._1).foreach { case (word, count) =>
    println(s"$word -> $count")
  }

  def wordCount(words: List[String]): Map[String, Int] = {
    // TODO: group equal words together, then count each group's size
    ???
  }
}
```

### Correction

Teacher-only — do not share with students. Upload [`03-word-counting.scala`](03-word-counting.scala) via the "Correction" file picker (must be a `.scala` file).

### Test cases

| Name | Call expression | Expected output | Trim | Tolerance |
|---|---|---|---|---|
| counts each word, sorted by key | `wordCount(List("A", "B", "A", "C", "B", "A")).toList.sortBy(_._1).mkString(",")` | `(A,3),(B,2),(C,1)` | off | — |

The call expression sorts before `mkString` for the same reason `main`
does: a bare `Map` has no guaranteed print order, and the platform's automated check
does an exact string comparison.

Verified locally by simulating how CodeVault's automated test-case check evaluates a call expression against the correction and the output matches the expected output above.

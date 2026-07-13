# 04 — List vs Vector Benchmark

> Adapted from [`sessions/session2/Session2Exercise2.scala`](../src/main/scala/sessions/session2/Session2Exercise2.scala) in this repo.

**Test-simplification note:** this exercise measures wall-clock time, so its
output is inherently non-deterministic — grading can't be "does the output
match exactly." The expected output below is a **format template**: check
the shape of the two printed lines and the *relative* result (`List` append
should be visibly slower than `Vector` append), not the exact millisecond
values, which vary by machine and JVM warm-up state.

## CodeVault exam fields

| Field | Value |
|---|---|
| Title | List vs Vector Benchmark |
| Language | `scala` |
| Exam type | `code` |
| Suggested duration | 15 minutes |

### Description

```markdown
`List` is a linked list: fast to prepend to (`O(1)`), slow to append to
(`O(n)`, since it has to walk the whole list to reach the end). `Vector` is a
tree-like structure with fast, roughly constant-time operations on both
ends. This exercise makes that difference visible.

Write a function:

def timeOperation(operation: => Unit): Long

It runs `operation`, measures how long it took in **milliseconds**, and
returns that duration. (`operation: => Unit` is a *by-name* parameter — the
block is passed unevaluated and only runs when `timeOperation` calls it.)

In `main`:
1. Build a `List` and a `Vector`, both containing 50,000 zeros
   (`List.fill(n)(0)` / `Vector.fill(n)(0)`).
2. Time a **prepend** (`1 +: collection`) on each.
3. Time an **append** (`collection :+ 1`) on each.
4. Print the two results in this exact format:

    Prepend : List = <t> ms, Vector = <t> ms
    Append  : List = <t> ms, Vector = <t> ms

Don't worry about getting the exact same millisecond numbers as anyone
else — that depends on the machine. What should hold true every time:
`List`'s append is the slow one.
```

### Starter code

```scala
object ListVsVector {

  def main(args: Array[String]): Unit = {
    val n = 50000
    val list = List.fill(n)(0)
    val vector = Vector.fill(n)(0)

    // TODO: time a prepend (1 +: collection) on each collection
    // TODO: time an append (collection :+ 1) on each collection
    // TODO: println the two results, matching the format in the description
  }

  def timeOperation(operation: => Unit): Long = {
    // TODO: record time before and after running `operation`, return the
    // elapsed time in milliseconds
    ???
  }
}
```

### Reference solution (teacher-only — do not share with students)

```scala
object ListVsVector {

  def main(args: Array[String]): Unit = {
    val n = 50000
    val list = List.fill(n)(0)
    val vector = Vector.fill(n)(0)

    val listPrependMs = timeOperation { 1 +: list }
    val vectorPrependMs = timeOperation { 1 +: vector }

    val listAppendMs = timeOperation { list :+ 1 }
    val vectorAppendMs = timeOperation { vector :+ 1 }

    println(s"Prepend : List = $listPrependMs ms, Vector = $vectorPrependMs ms")
    println(s"Append  : List = $listAppendMs ms, Vector = $vectorAppendMs ms")
  }

  def timeOperation(operation: => Unit): Long = {
    val start = System.nanoTime()
    operation
    val end = System.nanoTime()
    (end - start) / 1000000 // ms
  }
}
```

### Expected output (for grading — template, not exact match)

```text
Prepend : List = <t> ms, Vector = <t> ms
Append  : List = <t> ms, Vector = <t> ms
```

Sample run (values will differ on other machines):

```text
Prepend : List = 0 ms, Vector = 0 ms
Append  : List = 5 ms, Vector = 0 ms
```

Grading checklist: both lines present and correctly formatted; the `List`
append time is clearly ≥ the `Vector` append time.

Verified locally with `scala run 04.scala --server=false` (Scala 3), run
twice to confirm the pattern is stable.

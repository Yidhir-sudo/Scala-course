# 10 — Either: Safe Square Root

> Adapted from [`sessions/session5/Session5.scala`](../src/main/scala/sessions/session5/Session5.scala) (Exercise 2) in this repo.

## CodeVault exam fields

| Field | Value |
|---|---|
| Title | Either: Safe Square Root |
| Language | `scala` |
| Exam type | `code` |
| Suggested duration | 10 minutes |

### Description

```markdown
`math.sqrt` on a negative number returns `NaN` — a silent failure that's
easy to miss. `Either` makes the failure explicit in the return type
instead.

Write a function:

def safeSqrt(x: Double): Either[String, Double]

- If `x < 0`, return `Left("Negative number")`.
- Otherwise, return `Right(math.sqrt(x))`.

**Example**

    println(safeSqrt(9))   // Right(3.0)
    println(safeSqrt(-4))  // Left(Negative number)

Your `main` should call `safeSqrt(9)` then `safeSqrt(-4)`, printing each
result.
```

### Starter code

```scala
object EitherSafeSqrt {

  def main(args: Array[String]): Unit = {
    println(safeSqrt(9))
    println(safeSqrt(-4))
  }

  def safeSqrt(x: Double): Either[String, Double] = {
    // TODO: Left("Negative number") if x < 0, otherwise Right(math.sqrt(x))
    ???
  }
}
```

### Reference solution (teacher-only — do not share with students)

```scala
object EitherSafeSqrt {

  def main(args: Array[String]): Unit = {
    println(safeSqrt(9))
    println(safeSqrt(-4))
  }

  def safeSqrt(x: Double): Either[String, Double] =
    if (x < 0) Left("Negative number")
    else Right(math.sqrt(x))
}
```

### Expected output (for grading)

```text
Right(3.0)
Left(Negative number)
```

Verified locally with `scala run 10.scala --server=false` (Scala 3) — output
matches exactly.

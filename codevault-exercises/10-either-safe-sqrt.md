# 10 — Either: Safe Square Root

> Adapted from [`sessions/session5/Session5.scala`](../src/main/scala/sessions/session5/Session5.scala) (Exercise 2) in this repo.

## CodeVault exercise fields

| Field | Value |
|---|---|
| Title | Either: Safe Square Root |
| Exercise type | `code` |
| Language | `scala` |
| Course / Training | attach to exactly one — whichever holds session 5 |

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

Call `safeSqrt(9)` then `safeSqrt(-4)`, printing each result.
```

### Starter code

```scala
object Main extends App {

  println(safeSqrt(9))
  println(safeSqrt(-4))

  def safeSqrt(x: Double): Either[String, Double] = {
    // TODO: Left("Negative number") if x < 0, otherwise Right(math.sqrt(x))
    ???
  }
}
```

### Correction

Teacher-only — do not share with students. Upload [`10-either-safe-sqrt.scala`](10-either-safe-sqrt.scala) via the "Correction" file picker (must be a `.scala` file).

### Test cases

| Name | Call expression | Expected output | Trim | Tolerance |
|---|---|---|---|---|
| non-negative input | `safeSqrt(9)` | `Right(3.0)` | off | — |
| negative input | `safeSqrt(-4)` | `Left(Negative number)` | off | — |

Verified locally by simulating how CodeVault's automated test-case check evaluates a call expression against the correction and both match the expected outputs above.

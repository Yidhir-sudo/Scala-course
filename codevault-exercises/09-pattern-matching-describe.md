# 09 — Pattern Matching: Describe

> Adapted from [`sessions/session5/Session5.scala`](../src/main/scala/sessions/session5/Session5.scala) (Exercise 1) in this repo.

## CodeVault exercise fields

| Field | Value |
|---|---|
| Title | Pattern Matching: Describe |
| Exercise type | `code` |
| Language | `scala` |
| Course / Training | attach to exactly one — whichever holds session 5 |

### Description

```markdown
Write a function:

def describe(x: Any): String

Using `match`, return:
1. `"Positive number"` if `x` is an `Int` greater than 0.
2. `"Empty string"` if `x` is an empty `String`.
3. `"List of length N"` if `x` is a `List` (N = its length).
4. `"Unknown type"` for anything else.

**Example**

    println(describe(5))             // Positive number
    println(describe(""))            // Empty string
    println(describe(List(1, 2, 3))) // List of length 3
    println(describe(3.14))          // Unknown type

Call `describe` on exactly those four values, in that order, printing each
result.
```

### Starter code

```scala
object Main extends App {

  println(describe(5))
  println(describe(""))
  println(describe(List(1, 2, 3)))
  println(describe(3.14))

  def describe(x: Any): String = x match {
    // TODO: case for a positive Int
    // TODO: case for an empty String
    // TODO: case for a List (bind the length)
    // TODO: default case
    case _ => "Unknown type"
  }
}
```

### Correction

Teacher-only — do not share with students. Upload [`09-pattern-matching-describe.scala`](09-pattern-matching-describe.scala) via the "Correction" file picker (must be a `.scala` file).

### Test cases

| Name | Call expression | Expected output | Trim | Tolerance |
|---|---|---|---|---|
| positive Int | `describe(5)` | `Positive number` | off | — |
| empty String | `describe("")` | `Empty string` | off | — |
| a List | `describe(List(1, 2, 3))` | `List of length 3` | off | — |
| anything else | `describe(3.14)` | `Unknown type` | off | — |

Verified locally by simulating how CodeVault's automated test-case check evaluates a call expression against the correction and all four match the expected outputs above.

# 09 — Pattern Matching: Describe

> Adapted from [`sessions/session5/Session5.scala`](../src/main/scala/sessions/session5/Session5.scala) (Exercise 1) in this repo.

## CodeVault exam fields

| Field | Value |
|---|---|
| Title | Pattern Matching: Describe |
| Language | `scala` |
| Exam type | `code` |
| Suggested duration | 15 minutes |

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

Your `main` should call `describe` on exactly those four values, in that
order, printing each result.
```

### Starter code

```scala
object PatternMatchingDescribe {

  def main(args: Array[String]): Unit = {
    println(describe(5))
    println(describe(""))
    println(describe(List(1, 2, 3)))
    println(describe(3.14))
  }

  def describe(x: Any): String = x match {
    // TODO: case for a positive Int
    // TODO: case for an empty String
    // TODO: case for a List (bind the length)
    // TODO: default case
    case _ => "Unknown type"
  }
}
```

### Reference solution

Teacher-only — do not share with students. See [`09-pattern-matching-describe.scala`](09-pattern-matching-describe.scala).

### Expected output (for grading)

```text
Positive number
Empty string
List of length 3
Unknown type
```

Verified locally with `scala run 09.scala --server=false` (Scala 3) — output
matches exactly, no warnings.

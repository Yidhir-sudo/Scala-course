# 05 — Functional Pipeline: Numbers

> Adapted from [`sessions/session3/Session3.scala`](../src/main/scala/sessions/session3/Session3.scala) (Exercise 1) in this repo.

## CodeVault exam fields

| Field | Value |
|---|---|
| Title | Functional Pipeline: Numbers |
| Language | `scala` |
| Exam type | `code` |
| Suggested duration | 10 minutes |

### Description

```markdown
Write a function:

def processNumbers(nums: List[Int]): Int

It should, in order:
1. Keep only the even numbers.
2. Multiply each remaining number by 3.
3. Return the sum of what's left.

**Example**

    val nums = List(1, 2, 3, 4, 5, 6)
    processNumbers(nums) // 36   (2*3 + 4*3 + 6*3 = 6 + 12 + 18)

Your `main` should call `processNumbers` on that example list and print the
result.
```

### Starter code

```scala
object FunctionalPipelineNumbers {

  def main(args: Array[String]): Unit = {
    val nums = List(1, 2, 3, 4, 5, 6)
    println(processNumbers(nums))
  }

  def processNumbers(nums: List[Int]): Int = {
    // TODO: filter -> map -> sum, chained
    ???
  }
}
```

### Reference solution

Teacher-only — do not share with students. See [`05-functional-pipeline-numbers.scala`](05-functional-pipeline-numbers.scala).

### Expected output (for grading)

```text
36
```

Verified locally with `scala run 05.scala --server=false` (Scala 3) — output
matches exactly.

# 05 — Functional Pipeline: Numbers

> Adapted from [`sessions/session3/Session3.scala`](../src/main/scala/sessions/session3/Session3.scala) (Exercise 1) in this repo.

## CodeVault exercise fields

| Field | Value |
|---|---|
| Title | Functional Pipeline: Numbers |
| Exercise type | `code` |
| Language | `scala` |
| Course / Training | attach to exactly one — whichever holds session 3 |

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

Call `processNumbers` on that example list and print the result.
```

### Starter code

```scala
object Main extends App {

  val nums = List(1, 2, 3, 4, 5, 6)
  println(processNumbers(nums))

  def processNumbers(nums: List[Int]): Int = {
    // TODO: filter -> map -> sum, chained
    ???
  }
}
```

### Correction

Teacher-only — do not share with students. Upload [`05-functional-pipeline-numbers.scala`](05-functional-pipeline-numbers.scala) via the "Correction" file picker (must be a `.scala` file).

### Test cases

| Name | Call expression | Expected output | Trim | Tolerance |
|---|---|---|---|---|
| even numbers tripled and summed | `processNumbers(List(1, 2, 3, 4, 5, 6))` | `36` | off | — |

Verified locally by simulating how CodeVault's automated test-case check evaluates a call expression against the correction and the output matches the expected output above.

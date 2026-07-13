# 14 — Futures: Parallel Sum

> Adapted from [`sessions/session7/Session7Exercise2.scala`](../src/main/scala/sessions/session7/Session7Exercise2.scala) in this repo.

**⚠️ Platform-driven fix:** same issue as
[13 — Futures: Async Square](13-futures-async-square.md) — the original
prints via `result.foreach(sum => println(...))` then calls
`Await.ready(result, 10.seconds)`. `foreach` on a `Future` registers an
async callback exactly like `onComplete` does, so it has the same race:
locally this produced **zero output on 3 out of 3 runs**. Fixed here with
`Await.result`, which returns the value to the main thread so it can print
it directly (5/5 runs produced output after the fix).

## CodeVault exam fields

| Field | Value |
|---|---|
| Title | Futures: Parallel Sum |
| Language | `scala` |
| Exam type | `code` |
| Suggested duration | 15 minutes |

### Description

```markdown
Write a function:

def parallelSum(list: Vector[Int]): Future[Int]

It splits `list` in half with `splitAt`, sums each half in its own `Future`
(so the two halves are computed concurrently), and combines the two partial
sums into one `Future[Int]` using a `for`-comprehension.

**Why `Vector` and not `List`?** `splitAt` is `O(log n)` on a `Vector` but
`O(n)` on a `List` — so splitting a `List` this way would cancel out the
benefit of doing the two halves in parallel. This is the payoff from
[04 — List vs Vector Benchmark](04-list-vs-vector-benchmark.md).

In `main`, build `(1 to 1000000).toVector`, call `parallelSum`, then
`Await.result` it and print `"Total sum: <value>"`.

**Example output**

    Total sum: 1784293664

That number isn't a typo: `1` to `1,000,000` sums to `500,000,500,000`
mathematically, but `Vector[Int].sum` adds as 32-bit `Int`s, which wrap
around (overflow) well before reaching that total. The wrapped value above
is what a correct implementation actually — and deterministically —
produces; you're not expected to fix the overflow.
```

### Starter code

```scala
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object FuturesParallelSum {

  def main(args: Array[String]): Unit = {
    val data = (1 to 1000000).toVector
    val result = parallelSum(data)

    // TODO: Await.result(result, 10.seconds), then print "Total sum: <value>"
  }

  def parallelSum(list: Vector[Int]): Future[Int] = {
    // TODO: splitAt the midpoint, sum each half in its own Future,
    // combine both with a for-comprehension
    ???
  }
}
```

### Reference solution (teacher-only — do not share with students)

```scala
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object FuturesParallelSum {

  def main(args: Array[String]): Unit = {
    val data = (1 to 1000000).toVector
    val result = parallelSum(data)

    val sum = Await.result(result, 10.seconds)
    println(s"Total sum: $sum")
  }

  def parallelSum(list: Vector[Int]): Future[Int] = {
    val mid = list.length / 2
    val (left, right) = list.splitAt(mid)
    val f1 = Future { left.sum }
    val f2 = Future { right.sum }

    for {
      a <- f1
      b <- f2
    } yield a + b
  }
}
```

### Expected output (for grading)

```text
Total sum: 1784293664
```

Verified locally with `scala run 14.scala --server=false` (Scala 3), 5
consecutive runs, all identical.

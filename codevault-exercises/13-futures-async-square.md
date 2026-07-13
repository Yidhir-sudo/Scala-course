# 13 — Futures: Async Square

> Adapted from [`sessions/session7/Session7Exercise1.scala`](../src/main/scala/sessions/session7/Session7Exercise1.scala) in this repo.

**⚠️ Platform-driven fix (not just a style choice):** the original registers
its result via `result.onComplete { ... }` and then calls
`Await.ready(result, 5.seconds)`. That looks like it "waits for completion,"
but it doesn't — `Await.ready` only blocks until the `Future`'s *value* is
computed; it gives no guarantee that an already-registered `onComplete`
callback (which runs on a separate thread from the global `ExecutionContext`)
has actually finished executing before `main` returns and the JVM exits.
Running the original pattern locally with `scala run --server=false`
produced **zero output on 5 out of 5 runs** — the program exited before the
callback's `println` ever ran. This version uses `Await.result` instead,
which blocks the *main thread itself* until the value is available and lets
it print directly, removing the race entirely (5/5 runs produced output).
If you've seen a Scala Future example elsewhere that pairs `onComplete` with
`Await.ready` for "safety," it has the same latent bug.

## CodeVault exam fields

| Field | Value |
|---|---|
| Title | Futures: Async Square |
| Language | `scala` |
| Exam type | `code` |
| Suggested duration | 15 minutes |

### Description

```markdown
Write a function:

def computeSquareAsync(x: Int): Future[Int]

It returns a `Future` that (after a simulated delay of `Thread.sleep(1000)`,
to stand in for "slow work") completes with `x * x`.

You'll need these imports:

    import scala.concurrent._
    import scala.concurrent.ExecutionContext.Implicits.global
    import scala.concurrent.duration._
    import scala.util.{Success, Failure, Try}

In `main`, call `computeSquareAsync(6)`, then use `Await.result` to block
until it completes and get the `Int` value directly (wrap it in `Try` so you
can still branch on `Success`/`Failure`, e.g. if the future times out).
Print `"The square is: <value>"` on success, or `"Error: <message>"` on
failure.

**Example output**

    The square is: 36

**Why `Await.result` and not `onComplete` + `Await.ready`?** `onComplete`'s
callback runs on a background thread. `Await.ready` only waits for the
*value* to be ready, not for that callback to have actually run — so the
program can exit before the `println` inside `onComplete` ever fires.
`Await.result` blocks the thread that calls it and hands the value straight
back, so there's no race.
```

### Starter code

```scala
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.{Success, Failure, Try}

object FuturesAsyncSquare {

  def main(args: Array[String]): Unit = {
    val result = computeSquareAsync(6)

    // TODO: Await.result(result, 5.seconds) inside a Try, then match on
    // Success/Failure and print accordingly
  }

  def computeSquareAsync(x: Int): Future[Int] = Future {
    // TODO: Thread.sleep(1000), then return x * x
    ???
  }
}
```

### Reference solution (teacher-only — do not share with students)

```scala
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.{Success, Failure, Try}

object FuturesAsyncSquare {

  def main(args: Array[String]): Unit = {
    val result = computeSquareAsync(6)

    Try(Await.result(result, 5.seconds)) match {
      case Success(value) => println(s"The square is: $value")
      case Failure(e) => println(s"Error: ${e.getMessage}")
    }
  }

  def computeSquareAsync(x: Int): Future[Int] = Future {
    Thread.sleep(1000)
    x * x
  }
}
```

### Expected output (for grading)

```text
The square is: 36
```

Verified locally with `scala run 13.scala --server=false` (Scala 3), 5
consecutive runs, all identical.

# 13 — Futures: Async Square

> Adapted from [`sessions/session7/Session7Exercise1.scala`](../src/main/scala/sessions/session7/Session7Exercise1.scala) in this repo.

**⚠️ Platform-driven fixes (not just a style choice):**

1. The original registers its result via `result.onComplete { ... }` and
   then calls `Await.ready(result, 5.seconds)`. That looks like it "waits
   for completion," but it doesn't — `Await.ready` only blocks until the
   `Future`'s *value* is computed; it gives no guarantee that an
   already-registered `onComplete` callback (which runs on a separate
   thread from the global `ExecutionContext`) has actually finished
   executing before `main` returns and the JVM exits. Running the original
   pattern locally produced **zero output on 5 out of 5 runs** — the
   program exited before the callback's `println` ever ran. This version
   uses `Await.result` instead, which blocks the *main thread itself* until
   the value is available and lets it print directly, removing the race
   entirely (5/5 runs produced output). If you've seen a Scala `Future`
   example elsewhere that pairs `onComplete` with `Await.ready` for
   "safety," it has the same latent bug.

2. This exercise uses a plain `object Main { def main(args) = ... }`
   rather than `object Main extends App`. `App`'s `DelayedInit` machinery
   runs your top-level code as part of the object's own class
   initialization; blocking on a `Future` there can deadlock against the
   Future's own thread pool (a worker thread trying to touch the
   still-initializing class has to wait for that same initialization to
   finish). This reproduced consistently in local testing whenever the code
   used `extends App`, independent of anything else about the exercise. An
   explicit `def main` avoids it entirely. Worth knowing if you write your
   own `Future`-based exercises using `extends App`.

## CodeVault exercise fields

| Field | Value |
|---|---|
| Title | Futures: Async Square |
| Exercise type | `code` |
| Language | `scala` |
| Course / Training | attach to exactly one — whichever holds session 7 |

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

Call `computeSquareAsync(6)`, then use `Await.result` to block until it
completes and get the `Int` value directly (wrap it in `Try` so you can
still branch on `Success`/`Failure`, e.g. if the future times out). Print
`"The square is: <value>"` on success, or `"Error: <message>"` on failure.

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
object Main {
  import scala.concurrent._
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration._
  import scala.util.{Success, Failure, Try}

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

### Correction

Teacher-only — do not share with students. Upload [`13-futures-async-square.scala`](13-futures-async-square.scala) via the "Correction" file picker (must be a `.scala` file).

### Test cases

None. This exercise's whole point is a blocking wait for asynchronous work
— exactly the kind of code that risks the class-initialization deadlock
described above once something else evaluates an expression against it
alongside your own code. Review by eye against the expected output below
instead.

### Expected output (for manual review)

```text
The square is: 36
```

Verified locally, 5 consecutive runs, all identical.

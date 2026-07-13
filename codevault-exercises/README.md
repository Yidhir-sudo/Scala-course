# Scala Course Exercises, adapted for CodeVault

This directory adapts this repo's own exercises (sessions 1–7 plus the
mock exam and the real Spark exam under `src/main/scala/`) into a form
ready to paste into [CodeVault](https://github.com/Yidhir-sudo/codevault)'s
exam-creation form at **`/dashboard/exams`** (teacher/admin only).

The 9 original exercise groups became **16 standalone exercises**: each
session's 1–2 combined exercises were split into their own file, so the
catalog is both richer (more distinct things for a student to attempt) and
simpler (each one teaches a single idea).

Each file has the same structure:

- **CodeVault exam fields** — a table with the exact `title` /
  `language` / `exam_type` / `duration_minutes` to set.
- **Description** — paste directly into the "Description" field.
- **Starter code** — paste into "Starter code" (what the student sees
  first).
- **Reference solution** — for grading only, never share with students.
- **Expected output** — what a correct submission prints, for the teacher
  to compare against when grading (CodeVault grades manually — see below).

Every plain-`scala` exercise (01–15) was run locally with
`scala run <file> --server=false` (Scala 3, matching Piston's
`scala` version `3.2.2`) to confirm the "expected output" is what the
reference solution actually produces, not a guess. Exercise 16
(`spark_scala`) was verified against a local Spark install using the exact
command `backend/runner/app.py` uses to run submissions. See each file for
its own verification note.

## Why this needed adaptation, not just copy-paste

Three platform realities in this CodeVault codebase shaped every file here:

1. **No stdin.** `POST /api/execute` ([`backend/app/api/execute.py`](https://github.com/Yidhir-sudo/codevault/blob/main/backend/app/api/execute.py))
   only sends `{code, language}` — there's no field for feeding input to a
   running submission. The original guessing-game exercise
   (`Session1Exercise2.scala`) reads guesses via `scala.io.StdIn.readLine()`,
   which would just hang. [02 — Guess the Number](02-guess-the-number.md)
   rewrites it as a pure function over a fixed list of guesses instead.

2. **Single file, Scala 3.2.2, no test framework.** Piston runs one file
   (`{"files": [{"content": code}]}`) with no ScalaTest, no hidden test
   cases — grading is manual (`Submission.grade` /
   `grade_comment` in [`backend/app/models/submission.py`](https://github.com/Yidhir-sudo/codevault/blob/main/backend/app/models/submission.py)).
   That's why every exercise here bakes a fixed, deterministic `main` that
   prints clearly-labeled output, and every multi-file original (session 4's
   `Vehicle`/`Car`/`Bike`, session 6's `Shape`/`Circle`/`Rectangle`) got
   merged into one file.

3. **`Map` iteration order isn't guaranteed.** Several original exercises
   print a `Map` directly via `.mkString`. That can vary by Scala/library
   version even though it's usually stable within one version — not
   something you want to depend on for grading. Every exercise that returns
   a `Map` (03, 06, 15) sorts it by key before printing.

4. **`spark_scala` runs completely differently from `scala`.** It doesn't go
   through Piston at all — `backend/runner/app.py` feeds the file to
   `spark-shell -i`, which replays it as REPL commands. An `object` with a
   `def main` that's never explicitly called just sits there unexecuted.
   [16 — the Spark exam](16-spark-sales-analytics-exam.md) is a full
   structural rewrite to flat top-level statements for exactly this reason
   — see that file for the full explanation and verification transcript.

5. **`Await.ready` + `onComplete`/`foreach` is a latent race, not "safe."**
   The original session-7 exercises pair these to "wait for completion
   without an arbitrary sleep" — but `Await.ready` only waits for the
   `Future`'s *value*, not for an already-registered async callback to have
   *run*. Locally this produced **zero output on every run** (5/5 and 3/3).
   [13](13-futures-async-square.md) and
   [14](14-futures-parallel-sum.md) use `Await.result` instead, which
   blocks the calling thread and hands back the value directly — no race.
   Worth knowing if you see this pattern in other Scala teaching material.

## Exercise map

| # | File | Language | From (this repo) |
|---|---|---|---|
| 01 | [01-hello-and-loop.md](01-hello-and-loop.md) | `scala` | [`sessions/session1/Session1Exercise1.scala`](../src/main/scala/sessions/session1/Session1Exercise1.scala) |
| 02 | [02-guess-the-number.md](02-guess-the-number.md) | `scala` | [`sessions/session1/Session1Exercise2.scala`](../src/main/scala/sessions/session1/Session1Exercise2.scala) (rewritten, non-interactive) |
| 03 | [03-word-counting.md](03-word-counting.md) | `scala` | [`sessions/session2/Session2Exercise1.scala`](../src/main/scala/sessions/session2/Session2Exercise1.scala) |
| 04 | [04-list-vs-vector-benchmark.md](04-list-vs-vector-benchmark.md) | `scala` | [`sessions/session2/Session2Exercise2.scala`](../src/main/scala/sessions/session2/Session2Exercise2.scala) |
| 05 | [05-functional-pipeline-numbers.md](05-functional-pipeline-numbers.md) | `scala` | [`sessions/session3/Session3.scala`](../src/main/scala/sessions/session3/Session3.scala) (Ex. 1) |
| 06 | [06-functional-pipeline-text.md](06-functional-pipeline-text.md) | `scala` | [`sessions/session3/Session3.scala`](../src/main/scala/sessions/session3/Session3.scala) (Ex. 2) |
| 07 | [07-oop-rectangle.md](07-oop-rectangle.md) | `scala` | [`sessions/session4/classes/Rectangle.scala`](../src/main/scala/sessions/session4/classes/Rectangle.scala) + [`Session4.scala`](../src/main/scala/sessions/session4/Session4.scala) |
| 08 | [08-oop-vehicle-hierarchy.md](08-oop-vehicle-hierarchy.md) | `scala` | [`sessions/session4/classes/{Vehicle,Car,Bike}.scala`](../src/main/scala/sessions/session4/classes/) + [`Session4.scala`](../src/main/scala/sessions/session4/Session4.scala) |
| 09 | [09-pattern-matching-describe.md](09-pattern-matching-describe.md) | `scala` | [`sessions/session5/Session5.scala`](../src/main/scala/sessions/session5/Session5.scala) (Ex. 1) |
| 10 | [10-either-safe-sqrt.md](10-either-safe-sqrt.md) | `scala` | [`sessions/session5/Session5.scala`](../src/main/scala/sessions/session5/Session5.scala) (Ex. 2) |
| 11 | [11-generics-first-element.md](11-generics-first-element.md) | `scala` | [`sessions/session6/Session6.scala`](../src/main/scala/sessions/session6/Session6.scala) (Ex. 1) |
| 12 | [12-traits-shapes.md](12-traits-shapes.md) | `scala` | [`sessions/session6/classes/{Shape,Circle,Rectangle}.scala`](../src/main/scala/sessions/session6/classes/) + [`Session6.scala`](../src/main/scala/sessions/session6/Session6.scala) |
| 13 | [13-futures-async-square.md](13-futures-async-square.md) | `scala` | [`sessions/session7/Session7Exercise1.scala`](../src/main/scala/sessions/session7/Session7Exercise1.scala) (bug fixed, see above) |
| 14 | [14-futures-parallel-sum.md](14-futures-parallel-sum.md) | `scala` | [`sessions/session7/Session7Exercise2.scala`](../src/main/scala/sessions/session7/Session7Exercise2.scala) (bug fixed, see above) |
| 15 | [15-mock-exam-weather-records.md](15-mock-exam-weather-records.md) | `scala` | [`exams/exercises.md`](../src/main/scala/exams/exercises.md) + [`MockExamSolution.scala`](../src/main/scala/exams/MockExamSolution.scala) |
| 16 | [16-spark-sales-analytics-exam.md](16-spark-sales-analytics-exam.md) | `spark_scala` | [`exams/2026/M1_T2_IABD_RJ.md`](../src/main/scala/exams/2026/M1_T2_IABD_RJ.md) / [`.scala`](../src/main/scala/exams/2026/M1_T2_IABD_RJ.scala) (structural rewrite, see above) |

## Suggested difficulty ordering

Roughly matches the numbering above: 01–02 (absolute basics) → 03–06
(collections & functional pipelines) → 07–08 (OOP) → 09–10 (pattern
matching / `Either`) → 11–12 (generics & traits) → 13–14 (concurrency) →
15 (comprehensive practice exam) → 16 (advanced/optional — Spark).

## Linking this repo from CodeVault

CodeVault's `Course` model has a `git_url` field for exactly this — set it
from `/dashboard/courses` (shows as a "🔗 Exercises" link on the course
card) to point students at this repo for extra context, rather than
duplicating its content inside the CodeVault app repo itself.

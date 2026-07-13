# Scala Course — Exercises and Solutions

[![Scala](https://img.shields.io/badge/Scala-2.13.16-DC322F?logo=scala&logoColor=white)](https://www.scala-lang.org/)
[![sbt](https://img.shields.io/badge/sbt-1.11.5-blue)](https://www.scala-sbt.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](#-license)

A progressive Scala learning playground. Each session focuses on one core topic — from
the syntax basics to functional collections, OOP, pattern matching, generics, asynchronous
computation and beyond — with **statements** (`exercises.md`) and **fully worked solutions**.

---

## 📚 Table of Contents

- [Goals](#-goals)
- [Project Structure](#-project-structure)
- [Curriculum Overview](#-curriculum-overview)
- [CodeVault Exercises](#-codevault-exercises)
- [Requirements](#-requirements)
- [Getting Started](#-getting-started)
- [Running an Exercise](#-running-an-exercise)
- [Testing](#-testing)
- [Conventions](#-conventions)
- [Contributing](#-contributing)

---

## 🎯 Goals

- Provide a **single, opinionated entry point** for learning Scala 2.13 step by step.
- Keep every solution **idiomatic, immutable-first and tail-safe** whenever possible.
- Pair every solution with a short statement file (`exercises.md`) so a learner can
  attempt the problem **before** peeking at the answer.

---

## 📂 Project Structure

```text
.
├── build.sbt                          # Build definition (Scala 2.13, scalac options, ScalaTest)
├── project/
│   └── build.properties               # sbt version
├── src/
│   ├── main/scala/
│   │   ├── exams/                     # Mock exam solutions
│   │   │   └── MockExamSolution.scala
│   │   └── sessions/                  # Course sessions (1 → 8)
│   │       ├── session1/              # Syntax basics, I/O, control flow
│   │       │   ├── exercises.md
│   │       │   ├── Session1Exercise1.scala
│   │       │   ├── Session1Exercise2.scala
│   │       │   └── Session1Exercise2Improved.scala
│   │       ├── session2/              # Collections: List vs Vector, counting
│   │       ├── session3/              # Functional pipelines, case classes
│   │       ├── session4/              # OOP: classes, inheritance, polymorphism
│   │       ├── session5/              # Pattern matching, Either
│   │       ├── session6/              # Generics, traits, sealed hierarchies
│   │       ├── session7/              # Futures, async computation
│   │       └── session8/              # (Reserved for upcoming material)
│   └── test/scala/                    # ScalaTest test suites
├── codevault-exercises/               # These exercises, adapted for the CodeVault platform
│   ├── README.md                      # Mapping table + why each adaptation was needed
│   ├── 01-hello-and-loop.md           # Announcement (statement, starter code, expected output)
│   ├── 01-hello-and-loop.scala        # Answer (reference solution, teacher-only)
│   └── ...                            # 16 exercises total, each an .md + .scala pair
└── README.md                          # You are here
```

> Each `sessionN/` folder contains an `exercises.md` (statement) **and** the matching
> `SessionNExerciseX.scala` files (solution).

---

## 🧭 Curriculum Overview

| Session | Topic | Highlights |
|--------:|:------|:-----------|
| **1** | Scala basics | Variables, string interpolation, `for` loops, `StdIn` input, guessing game |
| **2** | Collections | Word counting with `groupBy`, performance: `List` vs `Vector` |
| **3** | Functional pipelines | `filter` / `map` / `groupBy`, `case class Record` |
| **4** | OOP | `Rectangle` class, inheritance (`Vehicle` → `Car`, `Bike`), polymorphism |
| **5** | Pattern matching | `describe(x: Any)`, `Either[String, Double]` with `safeSqrt` |
| **6** | Generics & traits | `firstElement[T]`, abstract `Shape` (`Circle`, `Rectangle`) |
| **7** | Concurrency | `Future`, `ExecutionContext`, parallel sum with `Vector.splitAt` |
| **Exam** | Mock exam | Aggregations on `Record` (average / min-max / daily summary) |

---

## 🎓 CodeVault Exercises

This course also feeds [CodeVault](https://github.com/Yidhir-sudo/codevault), a
platform where students write and run Scala/Spark code directly in the browser.
[`codevault-exercises/`](codevault-exercises/) re-packages the 9 exercise groups
above into **16 standalone exercises**, each a pair of files:

- **`NN-name.md`** — the announcement: statement, starter code, and expected
  output, ready to paste into CodeVault's exam-creation form.
- **`NN-name.scala`** — the answer: the complete reference solution,
  teacher-only.

The adaptation isn't just a reformat — CodeVault's sandbox has real
constraints this course's own exercises don't (no `stdin`, a single file per
submission, no test framework, and a completely different execution model
for Spark). Every exercise was re-verified against those constraints, which
caught a couple of real bugs along the way (a `Future` completion race in
the session 7 exercises, and a Spark script that would silently produce no
output at all). See [`codevault-exercises/README.md`](codevault-exercises/README.md)
for the full mapping and the story behind each fix.

---

## 🛠️ Requirements

- **JDK 11+** (17 recommended)
- **sbt 1.11.x** (pinned in `project/build.properties`)
- Scala **2.13.16** (resolved automatically by sbt)

Optional but recommended:

- [Metals](https://scalameta.org/metals/) for VS Code / IntelliJ Scala plugin
- [Coursier](https://get-coursier.io/) (`cs setup`) for a one-shot toolchain install

---

## 🚀 Getting Started

```bash
# 1. Clone
git clone <repository-url>
cd scala-course

# 2. Compile everything
sbt compile

# 3. Open an sbt shell (faster for repeated runs)
sbt
```

---

## ▶️ Running an Exercise

Each solution is a standalone `object` with a `main`. Run it through sbt by passing the
fully-qualified class name to `runMain`:

```bash
# Session 1 – Exercise 1
sbt "runMain sessions.session1.Session1Exercise1"

# Session 7 – parallel sum
sbt "runMain sessions.session7.Session7Exercise2"

# Mock exam
sbt "runMain exams.MockExamSolution"
```

> Inside the sbt shell you can shorten to `runMain sessions.session1.Session1Exercise1`.

---

## 🧪 Testing

ScalaTest is wired up in `build.sbt`. Drop your specs under `src/test/scala/` and run:

```bash
sbt test
# or for a single suite
sbt "testOnly sessions.session3.Session3Spec"
```

---

## 📐 Conventions

- **Immutability first**: prefer `val` over `var`, prefer pure functions.
- **Single-pass aggregations**: use `foldLeft` instead of chaining `map(...).sum` + `map(...).max`.
- **`Option` / `Either` over sentinel values**: never return `0.0` to signal "empty".
- **`Vector` for index / split-heavy work**, `List` for head-prepend / pattern matching.
- **scalac is strict**: `-deprecation -feature -unchecked -Xlint -Wunused` are enabled —
  fix warnings as you go.

---

## 🤝 Contributing

1. Create a branch: `git checkout -b feature/YMO/sessionX`
2. Add the statement under `sessions/sessionN/exercises.md`.
3. Add the solution(s) as `SessionNExerciseX.scala` in the same folder.
4. Make sure `sbt compile` and `sbt test` are green.
5. Open a Pull Request.

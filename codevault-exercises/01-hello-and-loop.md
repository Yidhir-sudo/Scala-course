# 01 — Hello and Loop

> Adapted from [`sessions/session1/Session1Exercise1.scala`](../src/main/scala/sessions/session1/Session1Exercise1.scala) in this repo.

## CodeVault exam fields

| Field | Value |
|---|---|
| Title | Hello and Loop |
| Language | `scala` |
| Exam type | `code` |
| Suggested duration | 10 minutes |

### Description

```markdown
Warm-up exercise: variables, string interpolation, and a `for` loop.

Write a program that:

1. Declares a `val name: String` with your name (or any name).
2. Prints `Hello, <name>!` using string interpolation (the `s"..."` syntax).
3. Prints the numbers from 1 to 10, one per line, using a `for` loop.

**Example** (if `name = "Ada"`):

\`\`\`text
Hello, Ada!
1
2
3
4
5
6
7
8
9
10
\`\`\`
```

### Starter code

```scala
object Hello {
  def main(args: Array[String]): Unit = {
    val name: String = "" // TODO: put your name here

    // TODO: print "Hello, <name>!" using string interpolation

    // TODO: print the numbers 1 to 10, one per line, using a for loop
  }
}
```

### Reference solution

Teacher-only — do not share with students. See [`01-hello-and-loop.scala`](01-hello-and-loop.scala).

### Expected output (for grading)

The exact greeting line depends on the `name` the student picks, but the
shape must match `Hello, <name>!` followed by the numbers 1 through 10, one
per line, in order — e.g. with `name = "Ada"`:

```text
Hello, Ada!
1
2
3
4
5
6
7
8
9
10
```

Verified locally with `scala run 01.scala --server=false` (Scala 3).

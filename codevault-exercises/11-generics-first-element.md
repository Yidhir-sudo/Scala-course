# 11 — Generics: First Element

> Adapted from [`sessions/session6/Session6.scala`](../src/main/scala/sessions/session6/Session6.scala) (Exercise 1) in this repo.

## CodeVault exam fields

| Field | Value |
|---|---|
| Title | Generics: First Element |
| Language | `scala` |
| Exam type | `code` |
| Suggested duration | 10 minutes |

### Description

```markdown
Write a **generic** function:

def firstElement[T](list: List[T]): Option[T]

It returns the first element of `list` wrapped in `Some`, or `None` if the
list is empty. `[T]` means the function works for a list of *any* type — you
don't write it once per type.

**Example**

    println(firstElement(List(1, 2, 3)))         // Some(1)
    println(firstElement(List("Scala", "Java"))) // Some(Scala)
    println(firstElement(List()))                // None

Your `main` should call `firstElement` on exactly those three lists, in that
order, printing each result. (Hint: `List` already has a method that does
exactly this — find it before writing your own logic.)
```

### Starter code

```scala
object GenericsFirstElement {

  def main(args: Array[String]): Unit = {
    println(firstElement(List(1, 2, 3)))
    println(firstElement(List("Scala", "Java")))
    println(firstElement(List()))
  }

  def firstElement[T](list: List[T]): Option[T] = {
    // TODO: return the first element as Some(...), or None if empty
    ???
  }
}
```

### Reference solution (teacher-only — do not share with students)

```scala
object GenericsFirstElement {

  def main(args: Array[String]): Unit = {
    println(firstElement(List(1, 2, 3)))
    println(firstElement(List("Scala", "Java")))
    println(firstElement(List()))
  }

  def firstElement[T](list: List[T]): Option[T] =
    list.headOption
}
```

### Expected output (for grading)

```text
Some(1)
Some(Scala)
None
```

Verified locally with `scala run 11.scala --server=false` (Scala 3) — output
matches exactly.

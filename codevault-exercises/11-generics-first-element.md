# 11 — Generics: First Element

> Adapted from [`sessions/session6/Session6.scala`](../src/main/scala/sessions/session6/Session6.scala) (Exercise 1) in this repo.

## CodeVault exercise fields

| Field | Value |
|---|---|
| Title | Generics: First Element |
| Exercise type | `code` |
| Language | `scala` |
| Course / Training | attach to exactly one — whichever holds session 6 |

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

Call `firstElement` on exactly those three lists, in that order, printing
each result. (Hint: `List` already has a method that does exactly this —
find it before writing your own logic.)
```

### Starter code

```scala
object Main extends App {

  println(firstElement(List(1, 2, 3)))
  println(firstElement(List("Scala", "Java")))
  println(firstElement(List()))

  def firstElement[T](list: List[T]): Option[T] = {
    // TODO: return the first element as Some(...), or None if empty
    ???
  }
}
```

### Correction

Teacher-only — do not share with students. Upload [`11-generics-first-element.scala`](11-generics-first-element.scala) via the "Correction" file picker (must be a `.scala` file).

### Test cases

| Name | Call expression | Expected output | Trim | Tolerance |
|---|---|---|---|---|
| Int list | `firstElement(List(1, 2, 3))` | `Some(1)` | off | — |
| String list | `firstElement(List("Scala", "Java"))` | `Some(Scala)` | off | — |
| empty list | `firstElement(List.empty[Int])` | `None` | off | — |

Verified locally by simulating how CodeVault's automated test-case check evaluates a call expression against the correction and all three match the expected outputs above.

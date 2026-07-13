# 07 — OOP: Rectangle

> Adapted from [`sessions/session4/classes/Rectangle.scala`](../src/main/scala/sessions/session4/classes/Rectangle.scala) + [`Session4.scala`](../src/main/scala/sessions/session4/Session4.scala) in this repo.

**Platform adaptation:** the original spreads the class and the demo across
two files under two packages. CodeVault's sandbox runs a single file per
submission, so the class and its `main` are merged here — the language
content is unchanged.

## CodeVault exam fields

| Field | Value |
|---|---|
| Title | OOP: Rectangle |
| Language | `scala` |
| Exam type | `code` |
| Suggested duration | 10 minutes |

### Description

```markdown
Create a `Rectangle` class with:
1. Two constructor parameters, `width` and `height` (both `Double`).
2. A method `area` that returns `width * height`.
3. A method `perimeter` that returns `2 * (width + height)`.

**Example**

    val rect = new Rectangle(5.0, 3.0)
    println(s"Area: ${rect.area}")           // Area: 15.0
    println(s"Perimeter: ${rect.perimeter}") // Perimeter: 16.0

Your `main` should create a `Rectangle(5.0, 3.0)` and print its area and
perimeter in that exact format.
```

### Starter code

```scala
class Rectangle(val width: Double, val height: Double) {
  // TODO: def area: Double = ...
  // TODO: def perimeter: Double = ...
}

object RectangleDemo {
  def main(args: Array[String]): Unit = {
    val rect = new Rectangle(5.0, 3.0)
    println(s"Area: ${rect.area}")
    println(s"Perimeter: ${rect.perimeter}")
  }
}
```

### Reference solution

Teacher-only — do not share with students. See [`07-oop-rectangle.scala`](07-oop-rectangle.scala).

### Expected output (for grading)

```text
Area: 15.0
Perimeter: 16.0
```

Verified locally with `scala run 07.scala --server=false` (Scala 3) — output
matches exactly.

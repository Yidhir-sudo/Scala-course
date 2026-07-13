# 07 — OOP: Rectangle

> Adapted from [`sessions/session4/classes/Rectangle.scala`](../src/main/scala/sessions/session4/classes/Rectangle.scala) + [`Session4.scala`](../src/main/scala/sessions/session4/Session4.scala) in this repo.

**Platform adaptation:** the original spreads the class and the demo across
two files under two packages. CodeVault's sandbox runs a single file per
submission, so the class and its usage are merged here. `Rectangle` is
nested *inside* the outer object (not a sibling top-level `class`) rather
than a stylistic choice — CodeVault's automated test cases evaluate a call
expression against your submission, and a bare `new Rectangle(...)` in that
expression needs `Rectangle` in the same top-level scope to resolve. None
of this changes the language content — inheritance and OOP mechanics are
identical either way.

## CodeVault exercise fields

| Field | Value |
|---|---|
| Title | OOP: Rectangle |
| Exercise type | `code` |
| Language | `scala` |
| Course / Training | attach to exactly one — whichever holds session 4 |

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

Create a `Rectangle(5.0, 3.0)` and print its area and perimeter in that
exact format.
```

### Starter code

```scala
object Main extends App {
  class Rectangle(val width: Double, val height: Double) {
    // TODO: def area: Double = ...
    // TODO: def perimeter: Double = ...
  }

  val rect = new Rectangle(5.0, 3.0)
  println(s"Area: ${rect.area}")
  println(s"Perimeter: ${rect.perimeter}")
}
```

### Correction

Teacher-only — do not share with students. Upload [`07-oop-rectangle.scala`](07-oop-rectangle.scala) via the "Correction" file picker (must be a `.scala` file).

### Test cases

| Name | Call expression | Expected output | Trim | Tolerance |
|---|---|---|---|---|
| area | `new Rectangle(5.0, 3.0).area` | `15.0` | off | `0.0001` |
| perimeter | `new Rectangle(5.0, 3.0).perimeter` | `16.0` | off | `0.0001` |

A numeric tolerance is used for both since they return `Double` — the
platform's automated check parses both sides as floats and compares within ±tolerance,
so harmless floating-point formatting differences can't fail a correct
answer.

Verified locally by simulating how CodeVault's automated test-case check evaluates a call expression against the correction and the output matches the expected output above.

# 12 — Traits & Abstraction: Shapes

> Adapted from [`sessions/session6/classes/{Shape,Circle,Rectangle}.scala`](../src/main/scala/sessions/session6/classes) + [`Session6.scala`](../src/main/scala/sessions/session6/Session6.scala) (Exercise 2) in this repo.

**Platform adaptation:** the original spreads `Shape`, `Circle`, `Rectangle`
and the demo across four files. Merged into one file here, with everything
nested inside a single outer object — CodeVault's automated test cases
call an expression directly against your submission, so anything a test
needs to reference has to live in the same top-level scope as the rest of
the code (see [07 — Rectangle](07-oop-rectangle.md) for the same note). The
language content (`abstract class`, shared interface, polymorphic
`.map(_.area).sum`) is unchanged.

## CodeVault exercise fields

| Field | Value |
|---|---|
| Title | Traits & Abstraction: Shapes |
| Exercise type | `code` |
| Language | `scala` |
| Course / Training | attach to exactly one — whichever holds session 6 |

### Description

```markdown
Create a small shape hierarchy:
1. An `abstract class Shape` with an abstract member `def area: Double`
   (no body — each subclass must supply its own).
2. A `Circle(radius: Double)` extending `Shape`, with
   `area = π * radius * radius`.
3. A `Rectangle(width: Double, height: Double)` extending `Shape`, with
   `area = width * height`.

Then write a function:

def totalArea(shapes: List[Shape]): Double

It returns the sum of the `area` of every shape in the list — it shouldn't
care whether each one is a `Circle` or a `Rectangle`, only that it's a
`Shape`.

**Example**

    val shapes = List(new Circle(2.0), new Rectangle(3.0, 4.0))
    println(totalArea(shapes)) // 24.566370614359172  (4π + 12)

Build that exact list and print `totalArea(shapes)`.
```

### Starter code

```scala
object Main extends App {
  abstract class Shape {
    def area: Double
  }

  class Circle(val radius: Double) extends Shape {
    // TODO: def area = π * radius * radius (use math.Pi)
  }

  class Rectangle(val width: Double, val height: Double) extends Shape {
    // TODO: def area = width * height
  }

  val shapes = List(new Circle(2.0), new Rectangle(3.0, 4.0))
  println(totalArea(shapes))

  def totalArea(shapes: List[Shape]): Double = {
    // TODO: sum the area of every shape in the list
    ???
  }
}
```

### Correction

Teacher-only — do not share with students. Upload [`12-traits-shapes.scala`](12-traits-shapes.scala) via the "Correction" file picker (must be a `.scala` file).

### Test cases

| Name | Call expression | Expected output | Trim | Tolerance |
|---|---|---|---|---|
| Circle area | `new Circle(2.0).area` | `12.566370614359172` | off | `0.0001` |
| Rectangle area | `new Rectangle(3.0, 4.0).area` | `12.0` | off | `0.0001` |
| total area across shapes | `totalArea(List(new Circle(2.0), new Rectangle(3.0, 4.0)))` | `24.566370614359172` | off | `0.0001` |

All three return `Double`, so a numeric tolerance is used — a small
rounding difference shouldn't fail a correct answer.

Verified locally by simulating how CodeVault's automated test-case check
evaluates each call expression against the correction — all three match
the expected outputs above.

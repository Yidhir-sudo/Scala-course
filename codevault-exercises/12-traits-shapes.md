# 12 — Traits & Abstraction: Shapes

> Adapted from [`sessions/session6/classes/{Shape,Circle,Rectangle}.scala`](../src/main/scala/sessions/session6/classes) + [`Session6.scala`](../src/main/scala/sessions/session6/Session6.scala) (Exercise 2) in this repo.

**Platform adaptation:** the original spreads `Shape`, `Circle`, `Rectangle`
and the demo across four files. Merged into one file for CodeVault's
single-file sandbox — the language content (`abstract class`, shared
interface, polymorphic `.map(_.area).sum`) is unchanged.

## CodeVault exam fields

| Field | Value |
|---|---|
| Title | Traits & Abstraction: Shapes |
| Language | `scala` |
| Exam type | `code` |
| Suggested duration | 15 minutes |

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

Your `main` should build that exact list and print `totalArea(shapes)`.
```

### Starter code

```scala
abstract class Shape {
  def area: Double
}

class Circle(val radius: Double) extends Shape {
  // TODO: def area = π * radius * radius (use math.Pi)
}

class Rectangle(val width: Double, val height: Double) extends Shape {
  // TODO: def area = width * height
}

object ShapesDemo {
  def main(args: Array[String]): Unit = {
    val shapes = List(new Circle(2.0), new Rectangle(3.0, 4.0))
    println(totalArea(shapes))
  }

  def totalArea(shapes: List[Shape]): Double = {
    // TODO: sum the area of every shape in the list
    ???
  }
}
```

### Reference solution (teacher-only — do not share with students)

```scala
abstract class Shape {
  def area: Double
}

class Circle(val radius: Double) extends Shape {
  def area: Double = math.Pi * radius * radius
}

class Rectangle(val width: Double, val height: Double) extends Shape {
  def area: Double = width * height
}

object ShapesDemo {
  def main(args: Array[String]): Unit = {
    val shapes = List(new Circle(2.0), new Rectangle(3.0, 4.0))
    println(totalArea(shapes))
  }

  def totalArea(shapes: List[Shape]): Double =
    shapes.map(_.area).sum
}
```

### Expected output (for grading)

```text
24.566370614359172
```

(This is `4π + 12` printed at full `Double` precision — a value ≈24.57 is
the correct area, but the grader should look for the literal digits above
since that's what `println` on a raw `Double` actually produces.)

Verified locally with `scala run 12.scala --server=false` (Scala 3) — output
matches exactly.

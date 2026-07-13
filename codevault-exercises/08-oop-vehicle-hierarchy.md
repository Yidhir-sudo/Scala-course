# 08 — OOP: Vehicle Hierarchy

> Adapted from [`sessions/session4/classes/{Vehicle,Car,Bike}.scala`](../src/main/scala/sessions/session4/classes) + [`Session4.scala`](../src/main/scala/sessions/session4/Session4.scala) in this repo.

**Platform adaptations:**
1. The original spreads `Vehicle`, `Car`, `Bike`, and the demo across four
   files, and each class's `move()` returns `Unit` and prints directly.
   Merged into one file here, with the classes nested inside the single
   outer `object` (same reason as [07 — Rectangle](07-oop-rectangle.md):
   CodeVault's automated test cases evaluate a call expression against your
   submission, so anything that expression references needs to be in the
   same top-level scope).
2. `move()` now **returns** the message as a `String` instead of printing
   it — `main` prints the returned value instead. A test case checks a call
   expression's return value, and a `Unit`-returning, print-only method has
   nothing to compare there (every `Unit` stringifies to the same `"()"`,
   whatever it printed along the way). Returning the message keeps the
   exact same inheritance/`override`/polymorphism lesson while making the
   exercise testable.

## CodeVault exercise fields

| Field | Value |
|---|---|
| Title | OOP: Vehicle Hierarchy |
| Exercise type | `code` |
| Language | `scala` |
| Course / Training | attach to exactly one — whichever holds session 4 |

### Description

```markdown
Create a small class hierarchy:
1. A `Vehicle` class with a method `move(): String` that returns
   `"The vehicle is moving"`.
2. A `Car` subclass that overrides `move()` to return
   `"The car is driving"`.
3. A `Bike` subclass that overrides `move()` to return
   `"The bike is rolling"`.

**Example**

    val vehicles: List[Vehicle] = List(new Car(), new Bike(), new Vehicle())
    vehicles.foreach(v => println(v.move()))
    // The car is driving
    // The bike is rolling
    // The vehicle is moving

Build that exact list, in that exact order, and print each element's
`move()` result — this is polymorphism: the same `.move()` call dispatches
to a different implementation depending on the actual runtime type of each
element.
```

### Starter code

```scala
object Main extends App {
  class Vehicle {
    def move(): String = "The vehicle is moving"
  }

  class Car extends Vehicle {
    // TODO: override move() to return "The car is driving"
  }

  class Bike extends Vehicle {
    // TODO: override move() to return "The bike is rolling"
  }

  val vehicles: List[Vehicle] = List(new Car(), new Bike(), new Vehicle())
  vehicles.foreach(v => println(v.move()))
}
```

### Correction

Teacher-only — do not share with students. Upload [`08-oop-vehicle-hierarchy.scala`](08-oop-vehicle-hierarchy.scala) via the "Correction" file picker (must be a `.scala` file).

### Test cases

| Name | Call expression | Expected output | Trim | Tolerance |
|---|---|---|---|---|
| Car overrides move | `new Car().move()` | `The car is driving` | off | — |
| Bike overrides move | `new Bike().move()` | `The bike is rolling` | off | — |
| base Vehicle move | `new Vehicle().move()` | `The vehicle is moving` | off | — |
| polymorphic dispatch through a list | `List(new Car(), new Bike(), new Vehicle()).map(_.move()).mkString(",")` | `The car is driving,The bike is rolling,The vehicle is moving` | off | — |

Verified locally by simulating how CodeVault's automated test-case check evaluates a call expression against the correction and all four match the expected outputs above.

# 08 — OOP: Vehicle Hierarchy

> Adapted from [`sessions/session4/classes/{Vehicle,Car,Bike}.scala`](../src/main/scala/sessions/session4/classes) + [`Session4.scala`](../src/main/scala/sessions/session4/Session4.scala) in this repo.

**Platform adaptation:** the original spreads `Vehicle`, `Car`, `Bike`, and
the demo across four files. Merged into one file for CodeVault's single-file
sandbox — the language content (inheritance, `override`, polymorphic
dispatch through a `List[Vehicle]`) is unchanged.

## CodeVault exam fields

| Field | Value |
|---|---|
| Title | OOP: Vehicle Hierarchy |
| Language | `scala` |
| Exam type | `code` |
| Suggested duration | 15 minutes |

### Description

```markdown
Create a small class hierarchy:
1. A `Vehicle` class with a method `move(): Unit` that prints
   `"The vehicle is moving"`.
2. A `Car` subclass that overrides `move()` to print
   `"The car is driving"`.
3. A `Bike` subclass that overrides `move()` to print
   `"The bike is rolling"`.

**Example**

    val vehicles: List[Vehicle] = List(new Car(), new Bike(), new Vehicle())
    vehicles.foreach(_.move())
    // The car is driving
    // The bike is rolling
    // The vehicle is moving

Your `main` should build that exact list, in that exact order, and call
`move()` on each element — this is polymorphism: the same `.move()` call
dispatches to a different implementation depending on the actual runtime
type of each element.
```

### Starter code

```scala
class Vehicle {
  def move(): Unit = println("The vehicle is moving")
}

class Car extends Vehicle {
  // TODO: override move() to print "The car is driving"
}

class Bike extends Vehicle {
  // TODO: override move() to print "The bike is rolling"
}

object VehicleDemo {
  def main(args: Array[String]): Unit = {
    val vehicles: List[Vehicle] = List(new Car(), new Bike(), new Vehicle())
    vehicles.foreach(_.move())
  }
}
```

### Reference solution (teacher-only — do not share with students)

```scala
class Vehicle {
  def move(): Unit = println("The vehicle is moving")
}

class Car extends Vehicle {
  override def move(): Unit = println("The car is driving")
}

class Bike extends Vehicle {
  override def move(): Unit = println("The bike is rolling")
}

object VehicleDemo {
  def main(args: Array[String]): Unit = {
    val vehicles: List[Vehicle] = List(new Car(), new Bike(), new Vehicle())
    vehicles.foreach(_.move())
  }
}
```

### Expected output (for grading)

```text
The car is driving
The bike is rolling
The vehicle is moving
```

Verified locally with `scala run 08.scala --server=false` (Scala 3) — output
matches exactly.

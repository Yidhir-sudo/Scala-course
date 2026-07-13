// Reference solution — OOP: Vehicle Hierarchy — teacher-only, do not share with students

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

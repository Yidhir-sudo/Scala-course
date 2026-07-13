object Main extends App {
  class Vehicle {
    def move(): String = "The vehicle is moving"
  }
  class Car extends Vehicle {
    override def move(): String = "The car is driving"
  }
  class Bike extends Vehicle {
    override def move(): String = "The bike is rolling"
  }

  val vehicles: List[Vehicle] = List(new Car(), new Bike(), new Vehicle())
  vehicles.foreach(v => println(v.move()))
}

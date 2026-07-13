object Main extends App {
  abstract class Shape {
    def area: Double
  }
  class Circle(val radius: Double) extends Shape {
    def area: Double = math.Pi * radius * radius
  }
  class Rectangle(val width: Double, val height: Double) extends Shape {
    def area: Double = width * height
  }

  val shapes = List(new Circle(2.0), new Rectangle(3.0, 4.0))
  println(totalArea(shapes))

  def totalArea(shapes: List[Shape]): Double =
    shapes.map(_.area).sum
}

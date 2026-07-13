object Main extends App {
  class Rectangle(val width: Double, val height: Double) {
    def area: Double = width * height
    def perimeter: Double = 2 * (width + height)
  }

  val rect = new Rectangle(5.0, 3.0)
  println(s"Area: ${rect.area}")
  println(s"Perimeter: ${rect.perimeter}")
}

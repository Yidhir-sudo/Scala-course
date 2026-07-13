// Reference solution — OOP: Rectangle — teacher-only, do not share with students

class Rectangle(val width: Double, val height: Double) {
  def area: Double = width * height
  def perimeter: Double = 2 * (width + height)
}

object RectangleDemo {
  def main(args: Array[String]): Unit = {
    val rect = new Rectangle(5.0, 3.0)
    println(s"Area: ${rect.area}")
    println(s"Perimeter: ${rect.perimeter}")
  }
}

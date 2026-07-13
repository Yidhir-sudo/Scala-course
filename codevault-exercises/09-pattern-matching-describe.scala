object Main extends App {
  println(describe(5))
  println(describe(""))
  println(describe(List(1, 2, 3)))
  println(describe(3.14))

  def describe(x: Any): String = x match {
    case i: Int if i > 0 => "Positive number"
    case s: String if s.isEmpty => "Empty string"
    case l: List[_] => s"List of length ${l.length}"
    case _ => "Unknown type"
  }
}

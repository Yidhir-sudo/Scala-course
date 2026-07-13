object Main extends App {
  println(firstElement(List(1, 2, 3)))
  println(firstElement(List("Scala", "Java")))
  println(firstElement(List()))

  def firstElement[T](list: List[T]): Option[T] = list.headOption
}

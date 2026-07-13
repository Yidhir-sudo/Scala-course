// Reference solution — Generics: First Element — teacher-only, do not share with students

object GenericsFirstElement {

  def main(args: Array[String]): Unit = {
    println(firstElement(List(1, 2, 3)))
    println(firstElement(List("Scala", "Java")))
    println(firstElement(List()))
  }

  def firstElement[T](list: List[T]): Option[T] =
    list.headOption
}

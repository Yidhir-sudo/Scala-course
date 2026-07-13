// Reference solution — List vs Vector Benchmark — teacher-only, do not share with students

object ListVsVector {

  def main(args: Array[String]): Unit = {
    val n = 50000
    val list = List.fill(n)(0)
    val vector = Vector.fill(n)(0)

    val listPrependMs = timeOperation { 1 +: list }
    val vectorPrependMs = timeOperation { 1 +: vector }

    val listAppendMs = timeOperation { list :+ 1 }
    val vectorAppendMs = timeOperation { vector :+ 1 }

    println(s"Prepend : List = $listPrependMs ms, Vector = $vectorPrependMs ms")
    println(s"Append  : List = $listAppendMs ms, Vector = $vectorAppendMs ms")
  }

  def timeOperation(operation: => Unit): Long = {
    val start = System.nanoTime()
    operation
    val end = System.nanoTime()
    (end - start) / 1000000 // ms
  }
}

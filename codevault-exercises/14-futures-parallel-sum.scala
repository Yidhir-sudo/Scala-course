// Reference solution — Futures: Parallel Sum — teacher-only, do not share with students

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object FuturesParallelSum {

  def main(args: Array[String]): Unit = {
    val data = (1 to 1000000).toVector
    val result = parallelSum(data)

    val sum = Await.result(result, 10.seconds)
    println(s"Total sum: $sum")
  }

  def parallelSum(list: Vector[Int]): Future[Int] = {
    val mid = list.length / 2
    val (left, right) = list.splitAt(mid)
    val f1 = Future { left.sum }
    val f2 = Future { right.sum }

    for {
      a <- f1
      b <- f2
    } yield a + b
  }
}

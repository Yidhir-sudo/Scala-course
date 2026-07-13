object Main {
  import scala.concurrent._
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration._
  import scala.util.{Success, Failure, Try}

  def main(args: Array[String]): Unit = {
    val result = computeSquareAsync(6)

    Try(Await.result(result, 5.seconds)) match {
      case Success(value) => println(s"The square is: $value")
      case Failure(e) => println(s"Error: ${e.getMessage}")
    }
  }

  def computeSquareAsync(x: Int): Future[Int] = Future {
    Thread.sleep(1000)
    x * x
  }
}

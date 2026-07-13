// Reference solution — Functional Pipeline: Numbers — teacher-only, do not share with students

object FunctionalPipelineNumbers {

  def main(args: Array[String]): Unit = {
    val nums = List(1, 2, 3, 4, 5, 6)
    println(processNumbers(nums))
  }

  def processNumbers(nums: List[Int]): Int =
    nums
      .filter(_ % 2 == 0)
      .map(_ * 3)
      .sum
}

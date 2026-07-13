object Main extends App {
  case class Record(city: String, date: String, temperature: Double, humidity: Double)

  val data = List(
    Record("Paris", "2025-03-01", 12.3, 0.65),
    Record("Paris", "2025-03-02", 15.1, 0.60),
    Record("Lyon", "2025-03-01", 10.5, 0.70),
    Record("Lyon", "2025-03-02", 9.8, 0.75),
    Record("Marseille", "2025-03-01", 17.2, 0.55),
    Record("Marseille", "2025-03-02", 18.4, 0.50)
  )

  val tempMin = 0.0
  val tempMax = 10.0

  println(s"1) Average temperature (all records): ${averageTemperature(data).getOrElse("Empty list")}")

  val byCity = averageTemperatureByCity(data).toList.sortBy(_._1)
    .map { case (city, avg) => s"$city -> $avg" }.mkString(", ")
  println(s"2) Average temperature by city: $byCity")

  println(s"3) Hottest city: ${hottestCity(data).getOrElse("Empty list")}")

  val normalized = normalizeTemperatures(data).mkString(", ")
  println(s"4) Normalized (Celsius -> Fahrenheit): $normalized")

  val filtered = filterByTemperature(data, tempMin, tempMax).mkString(", ")
  println(s"5) Records with temperature between $tempMin and $tempMax: $filtered")

  println(s"6) City with the largest temperature variation: ${cityWithMostVariation(data).getOrElse("Empty list")}")

  val summary = dailySummary(data).toList.sortBy(_._1)
    .map { case (date, (avgT, avgH)) => s"$date -> (avgTemp=$avgT, avgHumidity=$avgH)" }.mkString(", ")
  println(s"7) Daily summary: $summary")

  private def average(records: Seq[Record])(f: Record => Double): Option[Double] =
    if (records.isEmpty) None
    else Some(records.iterator.map(f).sum / records.size)

  def averageTemperature(data: List[Record]): Option[Double] =
    average(data)(_.temperature)

  def averageTemperatureByCity(data: List[Record]): Map[String, Double] =
    data
      .groupBy(_.city)
      .view
      .mapValues(records => records.iterator.map(_.temperature).sum / records.size)
      .toMap

  def hottestCity(data: List[Record]): Option[String] = {
    val avgByCity = averageTemperatureByCity(data)
    if (avgByCity.isEmpty) None
    else Some(avgByCity.maxBy(_._2)._1)
  }

  def normalizeTemperatures(data: List[Record]): List[Record] =
    data.map(r => r.copy(temperature = r.temperature * 9 / 5 + 32))

  def filterByTemperature(data: List[Record], min: Double, max: Double): List[Record] =
    data.filter(r => r.temperature >= min && r.temperature <= max)

  def cityWithMostVariation(data: List[Record]): Option[String] = {
    val cityVariation: Map[String, Double] = data
      .groupBy(_.city)
      .view
      .mapValues { records =>
        val (min, max) = records.foldLeft((Double.MaxValue, Double.MinValue)) {
          case ((mn, mx), r) => (math.min(mn, r.temperature), math.max(mx, r.temperature))
        }
        max - min
      }
      .toMap

    if (cityVariation.isEmpty) None
    else Some(cityVariation.maxBy(_._2)._1)
  }

  def dailySummary(data: List[Record]): Map[String, (Double, Double)] =
    data
      .groupBy(_.date)
      .view
      .mapValues { records =>
        val (sumT, sumH) = records.foldLeft((0.0, 0.0)) {
          case ((t, h), r) => (t + r.temperature, h + r.humidity)
        }
        val n = records.size
        (sumT / n, sumH / n)
      }
      .toMap
}

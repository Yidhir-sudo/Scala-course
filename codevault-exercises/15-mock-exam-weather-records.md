# 15 — Mock Exam: Weather Records

> Adapted from [`exams/exercises.md`](../src/main/scala/exams/exercises.md) + [`exams/MockExamSolution.scala`](../src/main/scala/exams/MockExamSolution.scala) in this repo. A good end-of-course practice exam: it combines `Option`, `groupBy`, `foldLeft`, and `case class.copy` from earlier sessions into one multi-part problem.

**Adaptations:**
1. The original prints two of the seven results via `Map.mkString`, whose
   entry order isn't guaranteed. This version sorts both maps by key before
   printing (by city name for Q2, by date for Q7) so the output is fully
   deterministic — verified with 3 separate runs producing byte-identical
   output.
2. `case class Record` and the sample `data` moved from inside `main` to
   the top of the single outer object, alongside the seven functions —
   partly for consistency with the other exercises here, and partly so a
   test case's call expression (e.g. `averageTemperature(data)`) can
   reference `data` directly rather than needing its own copy of the
   sample dataset.

## CodeVault exercise fields

| Field | Value |
|---|---|
| Title | Mock Exam: Weather Records |
| Exercise type | `code` |
| Language | `scala` |
| Course / Training | attach to exactly one — end-of-course practice, works well as a standalone training exercise |

### Description

```markdown
You're given a dataset of weather observations:

    case class Record(city: String, date: String, temperature: Double, humidity: Double)

    val data = List(
      Record("Paris",     "2025-03-01", 12.3, 0.65),
      Record("Paris",     "2025-03-02", 15.1, 0.60),
      Record("Lyon",      "2025-03-01", 10.5, 0.70),
      Record("Lyon",      "2025-03-02",  9.8, 0.75),
      Record("Marseille", "2025-03-01", 17.2, 0.55),
      Record("Marseille", "2025-03-02", 18.4, 0.50)
    )

Implement the following functions in an idiomatic, immutable, functional
style. Use `Option` when a result can be undefined (e.g. an empty list)
instead of a sentinel value like `0.0`.

**Q1 — Global average temperature**

    def averageTemperature(data: List[Record]): Option[Double]

Average temperature across all records, or `None` if `data` is empty.

**Q2 — Average temperature by city**

    def averageTemperatureByCity(data: List[Record]): Map[String, Double]

A map from city to that city's average temperature. (Hint: `groupBy` +
`mapValues`.)

**Q3 — Hottest city**

    def hottestCity(data: List[Record]): Option[String]

The city with the highest average temperature, or `None` if `data` is
empty.

**Q4 — Normalize temperatures (°C → °F)**

    def normalizeTemperatures(data: List[Record]): List[Record]

A new list where every record's temperature is converted with
`F = C * 9/5 + 32`. Other fields unchanged (`case class.copy` is your
friend here).

**Q5 — Filter by temperature range**

    def filterByTemperature(data: List[Record], min: Double, max: Double): List[Record]

Records whose temperature is between `min` and `max`, inclusive.

**Q6 — City with the largest temperature variation**

    def cityWithMostVariation(data: List[Record]): Option[String]

For each city, `max temperature - min temperature`; return the city with
the largest gap, or `None` if `data` is empty. Bonus: compute min and max in
a single pass with `foldLeft` instead of two separate traversals.

**Q7 — Daily summary**

    def dailySummary(data: List[Record]): Map[String, (Double, Double)]

A map from date to `(averageTemperature, averageHumidity)` for that day.
Bonus: compute both averages in a single `foldLeft` over each day's
records.

**Your program** should call all seven functions on the sample data above
and print one line per question, numbered `1)` through `7)`. For Q2 and Q7,
which return a `Map`, sort the entries by key before printing — a `Map`'s
own iteration order isn't guaranteed. Use `min = 0.0, max = 10.0` for Q5.
```

### Starter code

```scala
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

  // TODO: 1) print averageTemperature(data)
  // TODO: 2) print averageTemperatureByCity(data), sorted by city name
  // TODO: 3) print hottestCity(data)
  // TODO: 4) print normalizeTemperatures(data)
  // TODO: 5) print filterByTemperature(data, tempMin, tempMax)
  // TODO: 6) print cityWithMostVariation(data)
  // TODO: 7) print dailySummary(data), sorted by date

  def averageTemperature(data: List[Record]): Option[Double] = ???

  def averageTemperatureByCity(data: List[Record]): Map[String, Double] = ???

  def hottestCity(data: List[Record]): Option[String] = ???

  def normalizeTemperatures(data: List[Record]): List[Record] = ???

  def filterByTemperature(data: List[Record], min: Double, max: Double): List[Record] = ???

  def cityWithMostVariation(data: List[Record]): Option[String] = ???

  def dailySummary(data: List[Record]): Map[String, (Double, Double)] = ???
}
```

### Correction

Teacher-only — do not share with students. Upload [`15-mock-exam-weather-records.scala`](15-mock-exam-weather-records.scala) via the "Correction" file picker (must be a `.scala` file).

### Test cases

| Name | Call expression | Expected output | Trim | Tolerance |
|---|---|---|---|---|
| Q1 average temperature | `averageTemperature(data)` | `Some(13.883333333333335)` | off | — |
| Q2 average by city, sorted | `averageTemperatureByCity(data).toList.sortBy(_._1).mkString(",")` | `(Lyon,10.15),(Marseille,17.799999999999997),(Paris,13.7)` | off | — |
| Q3 hottest city | `hottestCity(data)` | `Some(Marseille)` | off | — |
| Q6 largest variation | `cityWithMostVariation(data)` | `Some(Paris)` | off | — |
| Q7 daily summary, sorted | `dailySummary(data).toList.sortBy(_._1).mkString(",")` | `(2025-03-01,(13.333333333333334,0.6333333333333334)),(2025-03-02,(14.433333333333332,0.6166666666666667))` | off | — |
| Q5 filter by range | `filterByTemperature(data, 0.0, 10.0).mkString(",")` | `Record(Lyon,2025-03-02,9.8,0.75)` | off | — |

Q4 (`normalizeTemperatures`) isn't covered by a test case — its result is a
full list of records, which is easy to eyeball in the expected output below
but awkward to pin to one exact string across environments.

Verified locally by simulating how CodeVault's automated test-case check
evaluates each call expression against the correction — all six match the
expected outputs above.

### Expected output (for manual review of Q4, or a full end-to-end read)

```text
1) Average temperature (all records): 13.883333333333335
2) Average temperature by city: Lyon -> 10.15, Marseille -> 17.799999999999997, Paris -> 13.7
3) Hottest city: Marseille
4) Normalized (Celsius -> Fahrenheit): Record(Paris,2025-03-01,54.14,0.65), Record(Paris,2025-03-02,59.18,0.6), Record(Lyon,2025-03-01,50.9,0.7), Record(Lyon,2025-03-02,49.64,0.75), Record(Marseille,2025-03-01,62.959999999999994,0.55), Record(Marseille,2025-03-02,65.12,0.5)
5) Records with temperature between 0.0 and 10.0: Record(Lyon,2025-03-02,9.8,0.75)
6) City with the largest temperature variation: Paris
7) Daily summary: 2025-03-01 -> (avgTemp=13.333333333333334, avgHumidity=0.6333333333333334), 2025-03-02 -> (avgTemp=14.433333333333332, avgHumidity=0.6166666666666667)
```

Sanity checks a reviewer can do by hand: Marseille's average (17.8) is
visibly the highest of the three cities (Q3 ✓); Paris has the widest
temperature spread (15.1 − 12.3 = 2.8 °C, vs 0.7 °C for Lyon and 1.2 °C for
Marseille), so Q6 = Paris ✓.

Verified locally, 3 consecutive runs, byte-identical output each time.

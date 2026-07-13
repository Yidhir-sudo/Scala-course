# 16 — Sales Analytics with Apache Spark

> Adapted from [`exams/2026/M1_T2_IABD_RJ.md`](../src/main/scala/exams/2026/M1_T2_IABD_RJ.md) + [`.scala`](../src/main/scala/exams/2026/M1_T2_IABD_RJ.scala) in this repo — the most advanced item in the set, a full DataFrame-API problem (window functions, pivots, joins, UDFs).

**⚠️ Platform adaptation — this one needed a structural rewrite, not just a
trim.** CodeVault's `spark_scala` language runs a submission more like
replaying a script line by line than compiling a single program — an
`object M1_T2_IABD_RJ { def main(args) = ... }` would just get *defined*
and then... nothing, since nothing ever calls `.main(Array())`. It would
compile fine and produce zero output.

The fix: this version drops the `object`/`def main` wrapper entirely and
writes the `case class`, the `SparkSession`, the ten functions, and the ten
`println(...); fooBar(df).show()` calls as **flat top-level statements**,
matching what the language actually expects. Verified end-to-end against a
local Spark install — all 10 tables render correctly, and Spark's own log
noise stays separate from the program's own output.

## CodeVault exercise fields

| Field | Value |
|---|---|
| Title | Sales Analytics with Apache Spark |
| Exercise type | `code` |
| Language | `spark_scala` |
| Course / Training | attach to exactly one — advanced/optional, pairs well with a Spark-focused course or training |

### Description

```markdown
You're given a dataset of product sales:

    case class Sale(product: String, category: String, date: String, quantity: Int, unitPrice: Double)

    val salesData = Seq(
      Sale("Laptop",    "Electronics", "2026-01-15",  3, 999.99),
      Sale("Phone",     "Electronics", "2026-01-15",  5, 699.99),
      Sale("Desk",      "Furniture",   "2026-01-16",  2, 349.99),
      Sale("Chair",     "Furniture",   "2026-01-16",  4, 199.99),
      Sale("Notebook",  "Stationery",  "2026-01-17", 10,   4.99),
      Sale("Pen",       "Stationery",  "2026-01-17", 50,   1.49),
      Sale("Monitor",   "Electronics", "2026-01-18",  2, 449.99),
      Sale("Keyboard",  "Electronics", "2026-01-18",  6,  89.99),
      Sale("Bookshelf", "Furniture",   "2026-01-19",  1, 279.99),
      Sale("Marker",    "Stationery",  "2026-01-19", 20,   2.99)
    )

    val discountData = Seq(
      ("Electronics", 0.10),
      ("Furniture",   0.05),
      ("Stationery",  0.20)
    ) // columns: category, discountRate

`revenue` of a row = `quantity * unitPrice`. Work exclusively with the
**DataFrame API** — no recursion, no mutable `var`s. This code runs as a
Spark shell script, so write it as **top-level statements** (`val`, `def`,
`case class` directly — no `object`/`def main` wrapper, and nothing you
define runs unless you call it as a top-level statement too).

Start with:

    import org.apache.spark.sql.{DataFrame, SparkSession}
    import org.apache.spark.sql.functions._
    import org.apache.spark.sql.expressions.Window

    case class Sale(product: String, category: String, date: String, quantity: Int, unitPrice: Double)

    val spark = SparkSession.builder().appName("SalesAnalytics").master("local[*]").getOrCreate()
    spark.sparkContext.setLogLevel("WARN")
    import spark.implicits._

    val df = salesData.toDF()
    val discountDf = discountData.toDF("category", "discountRate")

Then implement each function (all take a `DataFrame`, return a `DataFrame`
unless noted) and call it with `.show()`:

1. `salesSummary(df)` — one row, one `agg` call, columns `totalRevenue`
   (sum), `avgRevenue` (avg), `maxRevenue` (max), `minRevenue` (min) of
   `revenue`.
2. `revenueCategoryShare(df)` — columns `category`, `totalRevenue`,
   `sharePercent` (2 decimals) = that category's share of the grand total,
   sorted by `totalRevenue` descending. Hint: `groupBy`/`agg` for the
   per-category total, then a window over **all** rows
   (`Window.rowsBetween(Long.MinValue, Long.MaxValue)`) for the grand total.
3. `addPriceLabel(df)` — adds `priceLabel`: `"premium"` if `unitPrice > 500`,
   `"mid-range"` if `100 < unitPrice <= 500`, else `"budget"`. Chain
   `when(...).when(...).otherwise(...)`.
4. `topNByCategory(df, n)` — columns `category`, `product`, `revenue`,
   `rank`: the top `n` products per category by `revenue` descending, ties
   get the same rank. Hint: `dense_rank()` over
   `Window.partitionBy("category").orderBy(col("revenue").desc)`.
5. `cumulativeRevenueByDate(df)` — columns `date`, `dailyRevenue`,
   `cumulativeRevenue` (running total from the earliest date), sorted by
   date ascending. Hint: `Window.orderBy("date").rowsBetween(Long.MinValue, 0)`.
6. `quantityPivot(df)` — pivot table: one row per category, one column per
   date, cell = total `quantity` for that (category, date). Hint:
   `groupBy("category").pivot("date").agg(sum("quantity"))`.
7. `applyDiscounts(salesDf, discountDf)` — join on `category`, columns
   `product`, `category`, `revenue`, `discountRate`, `discountedRevenue`
   (2 decimals) = `revenue * (1 - discountRate)`.
8. `categoriesAboveAvgRevenue(df)` — columns `category`, `totalRevenue`,
   `overallAvg`: only categories whose total revenue is strictly above the
   mean category revenue.
9. `dailyRevenueVariation(df)` — columns `date`, `dailyRevenue`,
   `previousRevenue` (`null` for the first date), `variation` (2 decimals)
   = `dailyRevenue - previousRevenue`, sorted by date ascending. Hint:
   `lag("dailyRevenue", 1)` over `Window.orderBy("date")`.
10. `classifyRevenueUDF(df)` — columns `product`, `category`, `revenue`,
    `revenueClass`, computed by a **UDF**:
    `"high"` if `revenue >= 3000`, `"medium"` if `500 <= revenue < 3000`,
    else `"low"`. Hint:
    `val classify = udf((revenue: Double) => ...)`.

Print `"N) <description>:"` before each `.show()` call, in order 1 through
10.
```

### Starter code

```scala
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions.Window

case class Sale(product: String, category: String, date: String, quantity: Int, unitPrice: Double)

val spark = SparkSession.builder()
  .appName("SalesAnalytics")
  .master("local[*]")
  .getOrCreate()

spark.sparkContext.setLogLevel("WARN")

import spark.implicits._

val df = Seq(
  Sale("Laptop",    "Electronics", "2026-01-15",  3, 999.99),
  Sale("Phone",     "Electronics", "2026-01-15",  5, 699.99),
  Sale("Desk",      "Furniture",   "2026-01-16",  2, 349.99),
  Sale("Chair",     "Furniture",   "2026-01-16",  4, 199.99),
  Sale("Notebook",  "Stationery",  "2026-01-17", 10,   4.99),
  Sale("Pen",       "Stationery",  "2026-01-17", 50,   1.49),
  Sale("Monitor",   "Electronics", "2026-01-18",  2, 449.99),
  Sale("Keyboard",  "Electronics", "2026-01-18",  6,  89.99),
  Sale("Bookshelf", "Furniture",   "2026-01-19",  1, 279.99),
  Sale("Marker",    "Stationery",  "2026-01-19", 20,   2.99)
).toDF()

val discountDf = Seq(
  ("Electronics", 0.10),
  ("Furniture",   0.05),
  ("Stationery",  0.20)
).toDF("category", "discountRate")

// TODO: implement all ten functions below as top-level defs
def salesSummary(df: DataFrame): DataFrame = ???
def revenueCategoryShare(df: DataFrame): DataFrame = ???
def addPriceLabel(df: DataFrame): DataFrame = ???
def topNByCategory(df: DataFrame, n: Int): DataFrame = ???
def cumulativeRevenueByDate(df: DataFrame): DataFrame = ???
def quantityPivot(df: DataFrame): DataFrame = ???
def applyDiscounts(salesDf: DataFrame, discountDf: DataFrame): DataFrame = ???
def categoriesAboveAvgRevenue(df: DataFrame): DataFrame = ???
def dailyRevenueVariation(df: DataFrame): DataFrame = ???
def classifyRevenueUDF(df: DataFrame): DataFrame = ???

// TODO: call each function above with println("N) ...") + .show(), in order
```

### Correction

Teacher-only — do not share with students. Upload [`16-spark-sales-analytics-exam.scala`](16-spark-sales-analytics-exam.scala) via the "Correction" file picker (must be a `.scala` file).

### Test cases

None. CodeVault's automated test cases compare a single expression's
`.toString` against an expected string — a plain `.toString` on a
`DataFrame` only shows its schema, not the computed data, so a meaningful
check would need a `.collect()`-based expression per question. Given the
size of this exercise (ten questions) that's a lot of extra surface to get
exactly right, so this one is left to manual review for now; a teacher can
always add test cases later through the same UI.

### Expected output (for manual review)

Captured verbatim from the program's own printed output (Spark's own log
noise is kept separate and isn't part of this):

```text
1) Sales summary statistics:
+------------+-----------------+----------+------------------+
|totalRevenue|       avgRevenue|maxRevenue|        minRevenue|
+------------+-----------------+----------+------------------+
|     9903.97|990.3969999999999|   3499.95|49.900000000000006|
+------------+-----------------+----------+------------------+

2) Revenue share per category:
+-----------+------------------+------------+
|   category|      totalRevenue|sharePercent|
+-----------+------------------+------------+
|Electronics| 7939.839999999999|       80.17|
|  Furniture|           1779.93|       17.97|
| Stationery|184.20000000000002|        1.86|
+-----------+------------------+------------+

3) Products labelled by price tier:
+---------+-----------+----------+--------+---------+----------+
|  product|   category|      date|quantity|unitPrice|priceLabel|
+---------+-----------+----------+--------+---------+----------+
|   Laptop|Electronics|2026-01-15|       3|   999.99|   premium|
|    Phone|Electronics|2026-01-15|       5|   699.99|   premium|
|     Desk|  Furniture|2026-01-16|       2|   349.99| mid-range|
|    Chair|  Furniture|2026-01-16|       4|   199.99| mid-range|
| Notebook| Stationery|2026-01-17|      10|     4.99|    budget|
|      Pen| Stationery|2026-01-17|      50|     1.49|    budget|
|  Monitor|Electronics|2026-01-18|       2|   449.99| mid-range|
| Keyboard|Electronics|2026-01-18|       6|    89.99|    budget|
|Bookshelf|  Furniture|2026-01-19|       1|   279.99| mid-range|
|   Marker| Stationery|2026-01-19|      20|     2.99|    budget|
+---------+-----------+----------+--------+---------+----------+

4) Top 2 products per category by revenue:
+-----------+-------+------------------+----+
|   category|product|           revenue|rank|
+-----------+-------+------------------+----+
|Electronics|  Phone|           3499.95|   1|
|Electronics| Laptop|2999.9700000000003|   2|
|  Furniture|  Chair|            799.96|   1|
|  Furniture|   Desk|            699.98|   2|
| Stationery|    Pen|              74.5|   1|
| Stationery| Marker|59.800000000000004|   2|
+-----------+-------+------------------+----+

5) Cumulative revenue by date:
+----------+------------+-----------------+
|      date|dailyRevenue|cumulativeRevenue|
+----------+------------+-----------------+
|2026-01-15|     6499.92|          6499.92|
|2026-01-16|     1499.94|7999.860000000001|
|2026-01-17|       124.4|          8124.26|
|2026-01-18|     1439.92|          9564.18|
|2026-01-19|      339.79|9903.970000000001|
+----------+------------+-----------------+

6) Quantity pivot (category x date):
+-----------+----------+----------+----------+----------+----------+
|   category|2026-01-15|2026-01-16|2026-01-17|2026-01-18|2026-01-19|
+-----------+----------+----------+----------+----------+----------+
| Stationery|      NULL|      NULL|        60|      NULL|        20|
|Electronics|         8|      NULL|      NULL|         8|      NULL|
|  Furniture|      NULL|         6|      NULL|      NULL|         1|
+-----------+----------+----------+----------+----------+----------+

7) Sales with discounts applied:
+---------+-----------+------------------+------------+-----------------+
|  product|   category|           revenue|discountRate|discountedRevenue|
+---------+-----------+------------------+------------+-----------------+
|   Laptop|Electronics|2999.9700000000003|         0.1|          2699.97|
|    Phone|Electronics|           3499.95|         0.1|          3149.96|
|     Desk|  Furniture|            699.98|        0.05|           664.98|
|    Chair|  Furniture|            799.96|        0.05|           759.96|
| Notebook| Stationery|49.900000000000006|         0.2|            39.92|
|      Pen| Stationery|              74.5|         0.2|             59.6|
|  Monitor|Electronics|            899.98|         0.1|           809.98|
| Keyboard|Electronics| 539.9399999999999|         0.1|           485.95|
|Bookshelf|  Furniture|            279.99|        0.05|           265.99|
|   Marker| Stationery|59.800000000000004|         0.2|            47.84|
+---------+-----------+------------------+------------+-----------------+

8) Categories above average revenue:
+-----------+-----------------+------------------+
|   category|     totalRevenue|        overallAvg|
+-----------+-----------------+------------------+
|Electronics|7939.839999999999|3301.3233333333333|
+-----------+-----------------+------------------+

9) Daily revenue variation:
+----------+------------+---------------+---------+
|      date|dailyRevenue|previousRevenue|variation|
+----------+------------+---------------+---------+
|2026-01-15|     6499.92|           NULL|     NULL|
|2026-01-16|     1499.94|        6499.92| -4999.98|
|2026-01-17|       124.4|        1499.94| -1375.54|
|2026-01-18|     1439.92|          124.4|  1315.52|
|2026-01-19|      339.79|        1439.92| -1100.13|
+----------+------------+---------------+---------+

10) Revenue classification (UDF):
+---------+-----------+------------------+------------+
|  product|   category|           revenue|revenueClass|
+---------+-----------+------------------+------------+
|   Laptop|Electronics|2999.9700000000003|      medium|
|    Phone|Electronics|           3499.95|        high|
|     Desk|  Furniture|            699.98|      medium|
|    Chair|  Furniture|            799.96|      medium|
| Notebook| Stationery|49.900000000000006|         low|
|      Pen| Stationery|              74.5|         low|
|  Monitor|Electronics|            899.98|      medium|
| Keyboard|Electronics| 539.9399999999999|      medium|
|Bookshelf|  Furniture|            279.99|         low|
|   Marker| Stationery|59.800000000000004|         low|
+---------+-----------+------------------+------------+
```

**Verification method:** ran the adapted script end-to-end against a local
Spark install (close enough to what CodeVault runs for this purpose) — all
10 tables render correctly, with the program's own printed output kept
separate from Spark's log noise, matching what a student or teacher would
actually see.

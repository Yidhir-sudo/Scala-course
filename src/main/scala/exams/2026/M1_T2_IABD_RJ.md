# CC M1 — T2 — 4 IABD RJ - Sales Analytics with Apache Spark

**Duration:** 1 h 30  
**No recursion allowed.** Use the Spark DataFrame API: `filter`, `select`, `groupBy`, `agg`, `withColumn`, `orderBy`, `limit`, window functions, etc.

 

## Context

You are given a dataset of product sales. Each sale is represented by the following case class:

```scala
case class Sale(product: String, category: String, date: String, quantity: Int, unitPrice: Double)
```

You must create two Spark DataFrames from the following data and work exclusively with the **DataFrame API**.

**Sales data:**

```scala
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
```

**Discount table** (used in Question 7):

```scala
val discountData = Seq(
  ("Electronics", 0.10),
  ("Furniture",   0.05),
  ("Stationery",  0.20)
)
// columns: category, discountRate
```

The **revenue** of a sale row is defined as `quantity * unitPrice`.

### SparkSession initialisation

Create the SparkSession at the start of your `main` method as follows:

```scala
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions.Window

val spark = SparkSession.builder()
  .appName("M1_T2_IABD_RJ")
  .master("local[*]")
  .getOrCreate()

spark.sparkContext.setLogLevel("WARN")

import spark.implicits._

val df         = salesData.toDF()
val discountDf = discountData.toDF("category", "discountRate")
```

Each function takes a `DataFrame` as input and returns a `DataFrame` (or a scalar for Q1).  
All functions must be **pure and stateless** — no mutable variables, no recursion.

## Question 1 — Sales summary statistics

Write a function:

```scala
def salesSummary(df: DataFrame): DataFrame
```

It returns a **single-row** DataFrame with four columns computed in a **single `agg` call**:

| column | meaning |
|---|---|
| `totalRevenue` | sum of all `quantity * unitPrice` |
| `avgRevenue` | average `quantity * unitPrice` per row |
| `maxRevenue` | maximum `quantity * unitPrice` |
| `minRevenue` | minimum `quantity * unitPrice` |

## Question 2 — Revenue share per category

Write a function:

```scala
def revenueCategoryShare(df: DataFrame): DataFrame
```

It returns a DataFrame with columns `category`, `totalRevenue`, and `sharePercent` (rounded to 2 decimal places), sorted by `totalRevenue` descending.

`sharePercent` is the percentage of the category's revenue over the **grand total** of all categories.

**Hint:** compute `totalRevenue` per category with `groupBy`/`agg`, then use a window covering all rows

## Question 3 — Label products by price tier

Write a function:

```scala
def addPriceLabel(df: DataFrame): DataFrame
```

It returns the original DataFrame with an additional column `priceLabel`:

- `"premium"` if `unitPrice > 500`
- `"mid-range"` if `100 < unitPrice <= 500`
- `"budget"` otherwise

**Hint:** use chained `when(...)`.

## Question 4 — Top N products per category by revenue

Write a function:

```scala
def topNByCategory(df: DataFrame, n: Int): DataFrame
```

It returns a DataFrame with columns `category`, `product`, `revenue`, and `rank`, containing the **top `n` products per category** ranked by `revenue` descending. Ties receive the same rank.

**Hint:** add a `revenue` column, then use `dense_rank()` in a window.

## Question 5 — Cumulative revenue by date

Write a function:

```scala
def cumulativeRevenueByDate(df: DataFrame): DataFrame
```

It returns a DataFrame with columns `date`, `dailyRevenue`, and `cumulativeRevenue`, sorted by `date` ascending.

`cumulativeRevenue` is the running sum of `dailyRevenue` from the earliest date up to and including the current row.

**Hint:** use `rowsBetween()` in a window.

## Question 6 — Pivot: quantity sold per category per date

Write a function:

```scala
def quantityPivot(df: DataFrame): DataFrame
```

It returns a pivot table where each **row is a category** and each **column is a date**, with the cell value being the total `quantity` sold.

## Question 7 — Apply discounts

Write a function:

```scala
def applyDiscounts(salesDf: DataFrame, discountDf: DataFrame): DataFrame
```

It joins the two DataFrames on `category` and returns a DataFrame with columns `product`, `category`, `revenue`, `discountRate`, and `discountedRevenue` (rounded to 2 decimal places).

`discountedRevenue = revenue * (1 - discountRate)`

## Question 8 — Categories above average revenue

Write a function:

```scala
def categoriesAboveAvgRevenue(df: DataFrame): DataFrame
```

It returns a DataFrame with columns `category`, `totalRevenue`, and `overallAvg`, keeping only categories whose `totalRevenue` is **strictly above** the mean category revenue.

## Question 9 — Daily revenue variation

Write a function:

```scala
def dailyRevenueVariation(df: DataFrame): DataFrame
```

It returns a DataFrame with columns `date`, `dailyRevenue`, `previousRevenue`, and `variation` (rounded to 2 decimal places), sorted by `date` ascending.

- `previousRevenue` is the revenue of the **previous date** (`null` for the first date)
- `variation = dailyRevenue - previousRevenue`

**Hint:** aggregate total revenue per date and use `lag()`.

## Question 10 — User-Defined Function: classify revenue

Write a function:

```scala
def classifyRevenueUDF(df: DataFrame): DataFrame
```

It returns a DataFrame with columns `product`, `category`, `revenue`, and `revenueClass`.

`revenueClass` must be computed by a **UDF** you define:

- `"high"` if `revenue >= 3000`
- `"medium"` if `500 <= revenue < 3000`
- `"low"` if `revenue < 500`

**Hint:** `val classify = udf((revenue: Double) => ...)` then apply it with `withColumn`.

## Deliverable

A single file `last_name_NAME.scala` that compiles with `sbt compile` and runs via:

```bash
sbt "runMain last_name_name.scala"
```

The `last_name_NAME.scala` must be sent by teams to your teacher.

Your `main` should look like this :

```scala
  def main(args: Array[String]): Unit = {
    
    val spark = ...

    val df = Seq(...).toDF()

    val discountDf = Seq(...).toDF()

    println("1) Sales summary statistics:")
    salesSummary(df).show()

    println("2) Revenue share per category:")
    revenueCategoryShare(df).show()

    ...

    println("10) Revenue classification (UDF):")
    classifyRevenueUDF(df).show()

    spark.stop()
  }
```

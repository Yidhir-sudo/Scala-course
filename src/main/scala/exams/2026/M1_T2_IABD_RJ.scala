package exams

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions.Window

case class Sale(product: String, category: String, date: String, quantity: Int, unitPrice: Double)

object M1_T2_IABD_RJ {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("M1_T2_IABD_RJ")
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

    println("1) Sales summary statistics:")
    salesSummary(df).show()

    println("2) Revenue share per category:")
    revenueCategoryShare(df).show()

    println("3) Products labelled by price tier:")
    addPriceLabel(df).show()

    println("4) Top 2 products per category by revenue:")
    topNByCategory(df, 2).show()

    println("5) Cumulative revenue by date:")
    cumulativeRevenueByDate(df).show()

    println("6) Quantity pivot (category x date):")
    quantityPivot(df).show()

    println("7) Sales with discounts applied:")
    applyDiscounts(df, discountDf).show()

    println("8) Categories above average revenue:")
    categoriesAboveAvgRevenue(df).show()

    println("9) Daily revenue variation:")
    dailyRevenueVariation(df).show()

    println("10) Revenue classification (UDF):")
    classifyRevenueUDF(df).show()

    spark.stop()
  }

  // Question 1 — Multiple aggregations in a single agg call
  def salesSummary(df: DataFrame): DataFrame =
    df.agg(
      sum(col("quantity")  * col("unitPrice")).alias("totalRevenue"),
      avg(col("quantity")  * col("unitPrice")).alias("avgRevenue"),
      max(col("quantity")  * col("unitPrice")).alias("maxRevenue"),
      min(col("quantity")  * col("unitPrice")).alias("minRevenue")
    )

  // Question 2 — Revenue share per category using a full-table window
  def revenueCategoryShare(df: DataFrame): DataFrame = {
    val catRevenue = df
      .withColumn("revenue", col("quantity") * col("unitPrice"))
      .groupBy("category")
      .agg(sum("revenue").alias("totalRevenue"))

    val fullTable = Window.rowsBetween(Long.MinValue, Long.MaxValue)

    catRevenue
      .withColumn(
        "sharePercent",
        round(col("totalRevenue") / sum("totalRevenue").over(fullTable) * 100, 2)
      )
      .orderBy(col("totalRevenue").desc)
  }

  // Question 3 — Price tier label with chained when/otherwise
  def addPriceLabel(df: DataFrame): DataFrame =
    df.withColumn(
      "priceLabel",
      when(col("unitPrice") > 500, "premium")
        .when(col("unitPrice") > 100, "mid-range")
        .otherwise("budget")
    )

  // Question 4 — Top N products per category by revenue (dense_rank window)
  def topNByCategory(df: DataFrame, n: Int): DataFrame = {
    val window = Window.partitionBy("category").orderBy(col("revenue").desc)
    df.withColumn("revenue", col("quantity") * col("unitPrice"))
      .withColumn("rank", dense_rank().over(window))
      .filter(col("rank") <= n)
      .select("category", "product", "revenue", "rank")
      .orderBy("category", "rank")
  }

  // Question 5 — Cumulative revenue by date (rows-between window)
  def cumulativeRevenueByDate(df: DataFrame): DataFrame = {
    val dailyRevenue = df
      .withColumn("revenue", col("quantity") * col("unitPrice"))
      .groupBy("date")
      .agg(sum("revenue").alias("dailyRevenue"))
      .orderBy("date")

    val cumWindow = Window.orderBy("date").rowsBetween(Long.MinValue, 0)

    dailyRevenue
      .withColumn("cumulativeRevenue", sum("dailyRevenue").over(cumWindow))
  }

  // Question 6 — Pivot table: total quantity per category per date
  def quantityPivot(df: DataFrame): DataFrame =
    df.groupBy("category")
      .pivot("date")
      .agg(sum("quantity"))

  // Question 7 — Inner join with discount table + compute discounted revenue
  def applyDiscounts(salesDf: DataFrame, discountDf: DataFrame): DataFrame =
    salesDf
      .withColumn("revenue", col("quantity") * col("unitPrice"))
      .join(discountDf, "category")
      .withColumn("discountedRevenue", round(col("revenue") * (lit(1) - col("discountRate")), 2))
      .select("product", "category", "revenue", "discountRate", "discountedRevenue")

  // Question 8 — Categories whose total revenue exceeds the mean (full-table window)
  def categoriesAboveAvgRevenue(df: DataFrame): DataFrame = {
    val catRevenue = df
      .withColumn("revenue", col("quantity") * col("unitPrice"))
      .groupBy("category")
      .agg(sum("revenue").alias("totalRevenue"))

    val fullTable = Window.rowsBetween(Long.MinValue, Long.MaxValue)

    catRevenue
      .withColumn("overallAvg", avg("totalRevenue").over(fullTable))
      .filter(col("totalRevenue") > col("overallAvg"))
      .select("category", "totalRevenue", "overallAvg")
  }

  // Question 9 — Day-over-day revenue variation using lag window function
  def dailyRevenueVariation(df: DataFrame): DataFrame = {
    val dailyRevenue = df
      .withColumn("revenue", col("quantity") * col("unitPrice"))
      .groupBy("date")
      .agg(sum("revenue").alias("dailyRevenue"))
      .orderBy("date")

    val lagWindow = Window.orderBy("date")

    dailyRevenue
      .withColumn("previousRevenue", lag("dailyRevenue", 1).over(lagWindow))
      .withColumn("variation", round(col("dailyRevenue") - col("previousRevenue"), 2))
  }

  // Question 10 — UDF to classify a sale's revenue into low / medium / high
  def classifyRevenueUDF(df: DataFrame): DataFrame = {
    val classify = udf((revenue: Double) =>
      if (revenue >= 3000)     "high"
      else if (revenue >= 500) "medium"
      else                     "low"
    )
    df.withColumn("revenue", col("quantity") * col("unitPrice"))
      .withColumn("revenueClass", classify(col("revenue")))
      .select("product", "category", "revenue", "revenueClass")
  }
}


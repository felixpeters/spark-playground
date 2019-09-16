package com.example
import org.apache.spark.sql.SparkSession

object SalesDataPreprocessing {
  def main(args: Array[String]) {
    val spark = SparkSession.builder
      .appName("SalesDataPreprocessing")
      .getOrCreate()

    val salesPath = args(0)
    val storePath = args(1)
    val savePath = args(2)
    val sales = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv(salesPath)
    val stores = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv(storePath)

    val data = sales.join(stores, "Store")

    data.printSchema

    data.coalesce(1).write
      .format("csv")
      .mode("overwrite")
      .option("header", "true")
      .save(savePath)

    spark.stop()
  }
}

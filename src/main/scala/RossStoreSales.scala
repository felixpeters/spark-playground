package com.example
import org.apache.spark.sql.SparkSession

object RossStoreSales {
  def main(args: Array[String]) {
    val spark = SparkSession.builder
      .appName("RossStoreSales")
      .getOrCreate()

    val salesPath = args(0)
    val storePath = args(1)
    val sales = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv(salesPath)
    val stores = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv(storePath)

    sales.printSchema
    stores.printSchema

    spark.stop()
  }
}

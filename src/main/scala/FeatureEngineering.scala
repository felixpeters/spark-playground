package com.example
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.{Pipeline, PipelineModel}
import org.apache.spark.ml.feature.{StringIndexer, OneHotEncoderEstimator}

object FeatureEngineering {
  def main(args: Array[String]) {
    val spark = SparkSession.builder
      .appName("FeatureEngineering")
      .getOrCreate()

    val dataPath = args(0)

    val data = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv(dataPath)

    // convert string columns to numbers
    val indexCols = Array("StateHoliday", "StoreType", "Assortment", "PromoInterval")
    val indexers = indexCols.map { colName =>
      new StringIndexer()
        .setInputCol(colName)
        .setOutputCol(colName + "_indexed")
    }

    // one-hot encode categorical columns
    val catCols = Array(
      "DayOfWeek", 
      "StateHoliday_indexed", 
      "StoreType_indexed", 
      "Assortment_indexed",
      "PromoInterval_indexed"
    )
    val encoders = catCols.map { colName =>
      new OneHotEncoderEstimator()
        .setInputCols(Array(colName))
        .setOutputCols(Array(colName + "_vector"))
    }

    // create feature engineering pipeline
    val pipelineStages = indexers ++ encoders
    val pipeline = new Pipeline().setStages(pipelineStages)

    val features = pipeline.fit(data).transform(data)

    features.printSchema

    spark.stop()
  }
}

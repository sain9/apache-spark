package com.spark.job.spark_job.config;

import org.apache.spark.sql.SparkSession;

public class SparkConfig {

    public static SparkSession getSession() {
        SparkSession spark = SparkSession.builder()
                .appName("Spark Job")
                .master("local[*]")
                .getOrCreate();

        spark.sparkContext().setLogLevel("ERROR");
        return spark;
    }
}
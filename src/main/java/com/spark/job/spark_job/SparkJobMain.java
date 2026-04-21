package com.spark.job.spark_job;

import com.spark.job.spark_job.config.SparkConfig;
import com.spark.job.spark_job.job.SparkJob;
import org.apache.spark.sql.SparkSession;

public class SparkJobMain {

	public static void main(String[] args) {

		SparkSession spark = SparkConfig.getSession();

		SparkJob job = new SparkJob();
		job.run(spark);

		spark.stop();
	}
}
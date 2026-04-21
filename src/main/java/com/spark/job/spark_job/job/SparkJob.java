package com.spark.job.spark_job.job;

import com.spark.job.spark_job.reader.CsvReader;
import com.spark.job.spark_job.transformer.DataTransformer;
import com.spark.job.spark_job.writer.PostgresWriter;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class SparkJob {

    public void run(SparkSession spark) {

        CsvReader reader = new CsvReader();
        DataTransformer transformer = new DataTransformer();
        PostgresWriter writer = new PostgresWriter();

        // Step 1: Read
        Dataset<Row> df = reader.read(spark, "src/main/resources/sample.csv");

        // Step 2: Transform
        Dataset<Row> transformedDf = transformer.transform(df);

        // Step 3: Show (debug)
        transformedDf.show();

        // Step 4: Write
        writer.write(transformedDf,
                "jdbc:postgresql://localhost:5432/test",
                "people");

        System.out.println("Data inserted into PostgreSQL!");
    }
}
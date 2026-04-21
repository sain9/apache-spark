package com.spark.job.spark_job.transformer;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class DataTransformer {

    public Dataset<Row> transform(Dataset<Row> df) {
        // Example: remove nulls
        return df.na().drop();
    }
}
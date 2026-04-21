package com.spark.job.spark_job.writer;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;

import java.util.Properties;

public class PostgresWriter {

    public void write(Dataset<Row> df, String url, String table) {

        Properties props = new Properties();
        props.put("user", "postgres");
        props.put("password", "password");
        props.put("driver", "org.postgresql.Driver");

        df.write()
                .mode(SaveMode.Append)
                .jdbc(url, table, props);
    }
}
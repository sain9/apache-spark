package com.spark.job.spark_job.job;

import com.spark.job.spark_job.reader.CsvReader;
import com.spark.job.spark_job.transformer.DataTransformer;
import com.spark.job.spark_job.writer.PostgresWriter;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import static org.apache.spark.sql.functions.*;

public class SparkJob {

    public void run(SparkSession spark) {

        CsvReader reader = new CsvReader();
        DataTransformer transformer = new DataTransformer();
        PostgresWriter writer = new PostgresWriter();

        // Step 1: Read
//        Dataset<Row> df = reader.read(spark, "src/main/resources/sample.csv");

        // Step 2: Transform
//        Dataset<Row> transformedDf = transformer.transform(df);

        // Step 3: Show (debug)
//        transformedDf.show();

        // Step 4: Write
//        writer.write(transformedDf,
//                "jdbc:postgresql://localhost:5432/test",
//                "people");

//        Dataset<Row> filtered = df.filter("id IS NOT NULL AND name IS NOT NULL");
//        filtered.show();



//        Dataset<Row> updated = df.withColumn("name_upper", upper(col("name")));
//        updated.show();
//
//        Dataset<String> names = df.map(
//                (MapFunction<Row, String>) row -> row.getString(1).toUpperCase(),
//                Encoders.STRING()
//        );
//
//        Dataset<Row> exploded = df.withColumn(
//                "word",
//                explode(split(col("name"), " "))
//        );
//
//        exploded.show();
//
//        names.show();


//        Dataset<Row> grouped = df.groupBy("name").count();
//        grouped.show();

//        Dataset<Row> grouped = df.groupBy("name").count();
//        grouped.show();
//
//        Dataset<Row> sorted =  df.orderBy(col("id").desc());
//        sorted.show();
//        Dataset<Row> sorted = df.orderBy(col("id").desc());
//        sorted.show();

//        System.out.println("Data inserted into PostgreSQL!");


        Dataset<Row> peopleDf = reader.read(spark, "src/main/resources/sample.csv");
        peopleDf.show();

        Dataset<Row> ordersDf = reader.read(spark, "src/main/resources/orders.csv");
        ordersDf.show();

        Dataset<Row> joined = peopleDf.join(
                ordersDf,
                peopleDf.col("id").equalTo(ordersDf.col("customer_id")),
                "inner"
        );

        joined.show();


        Dataset<Row> sorted = peopleDf.orderBy(col("id").desc());
        sorted.show();


    }
}
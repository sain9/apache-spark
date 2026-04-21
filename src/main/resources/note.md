1) create your table
   CREATE TABLE people (
   id INT,
   name TEXT
   );


2) create sample.csv in resources
   id,name
   1,Hussain
   2,John
   3,Alice

--------------------------------------
3) create your mainclass as following:
-------------------------------------
package com.spark.job.spark_job;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

import java.util.Properties;

public class SparkJobMain {

	public static void main(String[] args) {

		SparkSession spark = SparkSession.builder()
				.appName("Spark Job")
				.master("local[*]")
				.getOrCreate();
		spark.sparkContext().setLogLevel("ERROR");

		System.out.println("Spark Started!");
		Dataset<Row> df = spark.read()
				.option("header", "true")
				.option("inferSchema", "true")
				.csv("src/main/resources/sample.csv");

		df.show();

		// DB config
		String url = "jdbc:postgresql://localhost:5432/test";

		Properties props = new Properties();
		props.put("user", "postgres");
		props.put("password", "password");
		props.put("driver", "org.postgresql.Driver");

		// Write to DB
		df.write()
				.mode(SaveMode.Append)
				.jdbc(url, "people", props);

		System.out.println("Data inserted into PostgreSQL!");

		spark.stop();
	}
}

-------------------------------------------------------
4) pom.xml [copy exact pom.xml as below]
------------------------------------------------------
<project xmlns="http://maven.apache.org/POM/4.0.0"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
http://maven.apache.org/xsd/maven-4.0.0.xsd">

	<modelVersion>4.0.0</modelVersion>

	<groupId>com.spark.job</groupId>
	<artifactId>spark-job</artifactId>
	<version>1.0-SNAPSHOT</version>

	<properties>
		<maven.compiler.source>17</maven.compiler.source>
		<maven.compiler.target>17</maven.compiler.target>
	</properties>

	<dependencies>

		<!-- Apache Spark -->
		<dependency>
			<groupId>org.apache.spark</groupId>
			<artifactId>spark-sql_2.12</artifactId>
			<version>3.5.0</version>
		</dependency>

		<dependency>
			<groupId>org.postgresql</groupId>
			<artifactId>postgresql</artifactId>
			<version>42.7.3</version>
		</dependency>




		<!-- Required for Spark UI (older javax, not jakarta) -->
<!--		<dependency>-->
<!--			<groupId>javax.servlet</groupId>-->
<!--			<artifactId>javax.servlet-api</artifactId>-->
<!--			<version>3.1.0</version>-->
<!--		</dependency>-->

	</dependencies>

	<build>
		<plugins>

			<!-- Java Compiler -->
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<version>3.11.0</version>
				<configuration>
					<source>17</source>
					<target>17</target>
				</configuration>
			</plugin>

			<!-- Build runnable JAR -->
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-jar-plugin</artifactId>
				<version>3.3.0</version>
				<configuration>
					<archive>
						<manifest>
							<mainClass>com.spark.job.spark_job.SparkJobMain</mainClass>
						</manifest>
					</archive>
				</configuration>
			</plugin>

		</plugins>
	</build>

</project>
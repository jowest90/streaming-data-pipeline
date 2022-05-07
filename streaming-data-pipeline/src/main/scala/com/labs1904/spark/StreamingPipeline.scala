package com.labs1904.spark

import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client.{ConnectionFactory, Get}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.log4j.Logger
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.{OutputMode, Trigger}

case class Review(marketplace: String,customer_id: Int,review_id: String,product_id: String,product_parent: Int,
                   product_title: String,product_category: String,star_rating: Int,helpful_votes: Int,total_votes: Int,
                   vine: String,verified_purchase: String,review_headline: String,review_body: String,review_date: String)

case class EnrichedReview(marketplace: String,customer_id: Int,review_id: String,product_id: String,product_parent: Int,
                          product_title: String,product_category: String,star_rating: Int,helpful_votes: Int,total_votes: Int,
                          vine: String,verified_purchase: String,review_headline: String,review_body: String,review_date: String,
                          name: String,username: String, mail: String,sex: String,birthdate: String)


/**
 * Spark Structured Streaming app
 *
 */
object StreamingPipeline {
  lazy val logger: Logger = Logger.getLogger(this.getClass)
  val jobName = "StreamingPipeline"

  val hdfsUrl = "CHANGEME"
  val bootstrapServers = "CHANGEME"
  val username = "CHANGEME"
  val password = "CHANGEME"
  val hdfsUsername = "CHANGEME" // TODO: set this to your handle
  val hdfsTable = "CHANGEME"
  val hdfsConfig = "CHANGEME"

  //Use this for Windows
  val trustStore: String = "src\\main\\resources\\kafka.client.truststore.jks"
  //Use this for Mac
  //val trustStore: String = "src/main/resources/kafka.client.truststore.jks"

  def main(args: Array[String]): Unit = {
    try {
//      val spark = SparkSession.builder()
//        .config("spark.sql.shuffle.partitions", "3")
//        .appName(jobName)
//        .master("local[*]")
//        .getOrCreate()

      val spark = SparkSession.builder()
        .config("spark.hadoop.dfs.client.use.datanode.hostname", "true")
        .config("spark.hadoop.fs.defaultFS", hdfsUrl)
        .config("spark.sql.shuffle.partitions", "3")
        .appName(jobName)
        .master("local[*]")
        .getOrCreate()

      import spark.implicits._

      val ds = spark
        .readStream
        .format("kafka")
        .option("kafka.bootstrap.servers", bootstrapServers)
        .option("subscribe", "reviews")
        .option("startingOffsets", "earliest")
        .option("maxOffsetsPerTrigger", "20")
        .option("startingOffsets","earliest")
        .option("kafka.security.protocol", "SASL_SSL")
        .option("kafka.sasl.mechanism", "SCRAM-SHA-512")
        .option("kafka.ssl.truststore.location", trustStore)
        .option("kafka.sasl.jaas.config", getScramAuthString(username, password))
        .load()
        .selectExpr("CAST(value AS STRING)").as[String]

      // TODO: implement logic here
      /*
      val result = ds

      // Write output to console
      val query = result.writeStream
        .outputMode(OutputMode.Append())
        .format("console")
        .option("truncate", false)
        .trigger(Trigger.ProcessingTime("5 seconds"))
        .start()
       */

      val result = ds.map(row => row.split("\t"))

      val reviews = result.map(row =>
        Review(row(0), row(1).toInt, row(2), row(3), row(4).toInt,
          row(5), row(6), row(7).toInt, row(8).toInt, row(9).toInt,
          row(10), row(11), row(12), row(13), row(14)))


      val customers = reviews.mapPartitions(partition => {
        // Open Hbase Connection
        val conf = HBaseConfiguration.create()
        conf.set("hbase.zookeeper.quorum", hdfsConfig)
        val connection = ConnectionFactory.createConnection(conf)

        val table = connection.getTable(TableName.valueOf(hdfsTable))

        val iter = partition.map(row => {
          val get = new Get(Bytes.toBytes(row.customer_id.toString)).addFamily(Bytes.toBytes("f1"))
          val result = table.get(get)
          val username = Bytes.toString(result.getValue(Bytes.toBytes("f1"),Bytes.toBytes("username")))
          val name = Bytes.toString(result.getValue(Bytes.toBytes("f1"),Bytes.toBytes("name")))
          val mail = Bytes.toString(result.getValue(Bytes.toBytes("f1"),Bytes.toBytes("mail")))
          val sex = Bytes.toString(result.getValue(Bytes.toBytes("f1"),Bytes.toBytes("sex")))
          val birthdate = Bytes.toString(result.getValue(Bytes.toBytes("f1"),Bytes.toBytes("birthdate")))
          EnrichedReview(row.marketplace, row.customer_id, row.review_id, row.product_id, row.product_parent,
            row.product_title, row.product_category, row.star_rating, row.helpful_votes, row.total_votes,
            row.vine, row.verified_purchase, row.review_headline, row.review_body, row.review_date,
            name, username, mail, sex, birthdate)
        }).toList.iterator

        // close connection
        connection.close()

        iter
      })

//      val query = customers.writeStream
//        .outputMode(OutputMode.Append())
//        .format("console")
//        .option("truncate", false)
//        .trigger(Trigger.ProcessingTime("5 seconds"))
//        .start()

      // Write output to HDFS
      val query = customers.writeStream
        .outputMode(OutputMode.Append())
        .format("csv")
        .option("path", s"/user/${hdfsUsername}/reviews_csv")
        .option("checkpointLocation", s"/user/${hdfsUsername}/reviews_checkpoint")
        .trigger(Trigger.ProcessingTime("5 seconds"))
        .start()
      query.awaitTermination()
    } catch {
      case e: Exception => logger.error(s"$jobName error in main", e)
    }
  }

  def getScramAuthString(username: String, password: String) = {
    s"""org.apache.kafka.common.security.scram.ScramLoginModule required
   username=\"$username\"
   password=\"$password\";"""
  }
}
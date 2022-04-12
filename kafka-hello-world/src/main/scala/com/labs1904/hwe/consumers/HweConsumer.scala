package com.labs1904.hwe.consumers

import com.labs1904.hwe.producers.SimpleProducer
import com.labs1904.hwe.util.Util
import com.labs1904.hwe.util.Util.{getScramAuthString, numberToWordMap}
import net.liftweb.json.DefaultFormats
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}
import org.apache.kafka.common.serialization.StringDeserializer

import java.time.Duration
import java.util.{Arrays, Properties, UUID}

case class RawUser(id: String, name: String, email: String) // constructor definition
case class EnrichedUser (id: String, numberAsWord: String, name: String,hweDeveloper: String, email: String) // constructor definition
object HweConsumer {
  val BootstrapServer : String = "CHANGEME"
  val consumerTopic: String = "question-1"
  val producerTopic: String = "question-1-output"
  val username: String = "CHANGEME"
  val password: String = "CHANGEME"
  //Use this for Windows
  val trustStore: String = "src\\main\\resources\\kafka.client.truststore.jks"
  //Use this for Mac
  //val trustStore: String = "src/main/resources/kafka.client.truststore.jks"

  implicit val formats: DefaultFormats.type = DefaultFormats

  def main(args: Array[String]): Unit = {

    // Create the KafkaConsumer
    val consumerProperties = SimpleConsumer.getProperties(BootstrapServer)
    val consumer: KafkaConsumer[String, String] = new KafkaConsumer[String, String](consumerProperties)

    // Create the KafkaProducer
    val producerProperties = SimpleProducer.getProperties(BootstrapServer)
    val producer = new KafkaProducer[String, String](producerProperties)

    // Subscribe to the topic
    consumer.subscribe(Arrays.asList(consumerTopic))

    while ( {
      true
    }) {
      // poll for new data
      val duration: Duration = Duration.ofMillis(100)
      val records: ConsumerRecords[String, String] = consumer.poll(duration)

      records.forEach((record: ConsumerRecord[String, String]) => {
        // Retrieve the message from each record
        val message = record.value()
//        println(s"Message Received: $message")

        // TODO: Add business logic here!
        //split lines by comma
        val user = message.split(",").map(_.trim)
        //identify values
        val id = user(0)
        val numberAsWord = numberToWordMap(user(0).toInt)
        val name = user(1)
        val hweDeveloper = "John West"
        val email = user(2)
        //RawUser
        val rawUserLog = new RawUser(id,name,email)
//        println(s"Raw User Received: "+rawUserLog.id+ " "+rawUserLog.name+ " "+rawUserLog.email)
//        println(s"Raw User Received: "+rawUserLog)
//        println(rawUserLog)
        //EnrichedUser
        val enrichedUserLog = new EnrichedUser(id,numberAsWord,name,hweDeveloper,email)
//        println(s"Enriched User Received: "+enrichedUserLog.id+ " "+enrichedUserLog.numberAsWord+" "+enrichedUserLog.name+ " "+enrichedUserLog.hweDeveloper+ " "+enrichedUserLog.email)
//        println(s"Enriched User Received: "+enrichedUserLog)
//        println(enrichedUserLog)

        //PRODUCER
        // create n fake records to send to topic
        val recordsToCreate = 1
        val range = (1 to recordsToCreate).toList

        range.map(id => {
          val key = enrichedUserLog.numberAsWord

          // Use the faker library ( https://github.com/bitblitconsulting/scala-faker ) to generate Users
          // User, as a case class, is defined at the top of this file
          //        val name = Name.name
          //        val user = User(name, Internet.user_name(name), Internet.free_email(name))

          // Switching to use CSV instead of JSON
          //val jsonString = write(user)
          val csvString = key + "," + name.replace(",","") + "," + enrichedUserLog.email.replace(",","")

          new ProducerRecord[String, String](producerTopic, key, csvString)
        }).foreach(record => {

          // send records to topic
          producer.send(record, new Callback() {
            override def onCompletion(recordMetadata: RecordMetadata, e: Exception): Unit = {
              if (e == null) {
                println(
                  s"""
                     |Sent Record: ${record.value()}
                     |Topic: ${recordMetadata.topic()}
                     |Partition: ${recordMetadata.partition()}
                     |Offset: ${recordMetadata.offset()}
                     |Timestamp: ${recordMetadata.timestamp()}
          """.stripMargin)
              } else println("Error while producing", e)
            }
          })
        })
      })
    }
    producer.close()
  }
}
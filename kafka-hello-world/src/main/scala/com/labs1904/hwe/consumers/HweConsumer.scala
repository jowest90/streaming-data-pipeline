package com.labs1904.hwe.consumers

import com.labs1904.hwe.producers.SimpleProducer
import com.labs1904.hwe.util.Util
import com.labs1904.hwe.util.Util.getScramAuthString
import net.liftweb.json.DefaultFormats
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.StringDeserializer

import java.time.Duration
import java.util.{Arrays, Properties, UUID}

case class RawUser(id: String, name: String, email: String) // constructor definition
case class EnrichedUser (id: String, numberAsWord: Integer, name: String,hweDeveloper: String, email: String) // constructor definition
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
        println(s"Message Received: $message")
        // TODO: Add business logic here!
        //split lines by comma
        val user = message.split(",").map(_.trim)
        //identify values
        val id = user(0)
        val numberAsWord = user(0).toInt
        val name = user(1)
        val hweDeveloper = "John West"
        val email = user(2)
        //RawUser
        val rawUserLog = new RawUser(id,name,email)
        println(rawUserLog)

        //EnrichedUser
        val enrichedUserLog = new EnrichedUser(id,numberAsWord,name,hweDeveloper,email)
        println(enrichedUserLog)

      })
    }
  }
}
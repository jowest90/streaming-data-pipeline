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
        /*
        OUTPUT:
Message Received: 1,Deanna Kihn,kihndeanna@yahoo.com
Message Received: 5,Bartholome Spinka IV,iv_spinka_bartholome@yahoo.com
Message Received: 7,Afton Goyette,goyette.afton@hotmail.com
Message Received: 8,Edwin Lind,lind.edwin@yahoo.com
Message Received: 1,Alyson Kessler IV,kessleralysoniv@gmail.com
Message Received: 5,Miss Cortney Labadie,cortney_labadie_miss@hotmail.com
Message Received: 7,Giovani Wisozk,giovani.wisozk@hotmail.com
Message Received: 8,Roel Hintz,hintz.roel@yahoo.com
Message Received: 4,Vicky Wunsch II,wunschiivicky@yahoo.com
Message Received: 6,Flavio Klocko,flavioklocko@hotmail.com
Message Received: 10,Matilde Sporer,sporermatilde@yahoo.com
Message Received: 4,Frances Harris,frances_harris@yahoo.com
Message Received: 6,Morton Monahan,monahan.morton@hotmail.com
Message Received: 10,Karli Weimann,karli_weimann@gmail.com
Message Received: 2,Abelardo O'Keefe,abelardoo'keefe@hotmail.com
Message Received: 3,Enrique Kerluke,kerluke.enrique@yahoo.com
Message Received: 9,Kiley Will DDS,will_dds_kiley@hotmail.com
Message Received: 2,Jena Koch,koch_jena@gmail.com
Message Received: 3,Gail Keeling,gailkeeling@yahoo.com
Message Received: 9,Gerson Shields,gerson.shields@hotmail.com
         */
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
//        println(s"Raw User Received: "+rawUserLog.id+ " "+rawUserLog.name+ " "+rawUserLog.email)
        /*
        OUTPUT:
Raw User Received: 4 Vicky Wunsch II wunschiivicky@yahoo.com
Raw User Received: 6 Flavio Klocko flavioklocko@hotmail.com
Raw User Received: 10 Matilde Sporer sporermatilde@yahoo.com
Raw User Received: 4 Frances Harris frances_harris@yahoo.com
Raw User Received: 6 Morton Monahan monahan.morton@hotmail.com
Raw User Received: 10 Karli Weimann karli_weimann@gmail.com
Raw User Received: 1 Deanna Kihn kihndeanna@yahoo.com
Raw User Received: 5 Bartholome Spinka IV iv_spinka_bartholome@yahoo.com
Raw User Received: 7 Afton Goyette goyette.afton@hotmail.com
Raw User Received: 8 Edwin Lind lind.edwin@yahoo.com
Raw User Received: 1 Alyson Kessler IV kessleralysoniv@gmail.com
Raw User Received: 5 Miss Cortney Labadie cortney_labadie_miss@hotmail.com
Raw User Received: 7 Giovani Wisozk giovani.wisozk@hotmail.com
Raw User Received: 8 Roel Hintz hintz.roel@yahoo.com
Raw User Received: 2 Abelardo O'Keefe abelardoo'keefe@hotmail.com
Raw User Received: 3 Enrique Kerluke kerluke.enrique@yahoo.com
Raw User Received: 9 Kiley Will DDS will_dds_kiley@hotmail.com
Raw User Received: 2 Jena Koch koch_jena@gmail.com
Raw User Received: 3 Gail Keeling gailkeeling@yahoo.com
Raw User Received: 9 Gerson Shields gerson.shields@hotmail.com
         */
//        println(s"Raw User Received: "+rawUserLog)
        /*
        OUTPUT:
Raw User Received: RawUser(4,Vicky Wunsch II,wunschiivicky@yahoo.com)
Raw User Received: RawUser(6,Flavio Klocko,flavioklocko@hotmail.com)
Raw User Received: RawUser(10,Matilde Sporer,sporermatilde@yahoo.com)
Raw User Received: RawUser(4,Frances Harris,frances_harris@yahoo.com)
Raw User Received: RawUser(6,Morton Monahan,monahan.morton@hotmail.com)
Raw User Received: RawUser(10,Karli Weimann,karli_weimann@gmail.com)
Raw User Received: RawUser(1,Deanna Kihn,kihndeanna@yahoo.com)
Raw User Received: RawUser(5,Bartholome Spinka IV,iv_spinka_bartholome@yahoo.com)
Raw User Received: RawUser(7,Afton Goyette,goyette.afton@hotmail.com)
Raw User Received: RawUser(8,Edwin Lind,lind.edwin@yahoo.com)
Raw User Received: RawUser(1,Alyson Kessler IV,kessleralysoniv@gmail.com)
Raw User Received: RawUser(5,Miss Cortney Labadie,cortney_labadie_miss@hotmail.com)
Raw User Received: RawUser(7,Giovani Wisozk,giovani.wisozk@hotmail.com)
Raw User Received: RawUser(8,Roel Hintz,hintz.roel@yahoo.com)
Raw User Received: RawUser(2,Abelardo O'Keefe,abelardoo'keefe@hotmail.com)
Raw User Received: RawUser(3,Enrique Kerluke,kerluke.enrique@yahoo.com)
Raw User Received: RawUser(9,Kiley Will DDS,will_dds_kiley@hotmail.com)
Raw User Received: RawUser(2,Jena Koch,koch_jena@gmail.com)
Raw User Received: RawUser(3,Gail Keeling,gailkeeling@yahoo.com)
Raw User Received: RawUser(9,Gerson Shields,gerson.shields@hotmail.com)
         */
//        println(rawUserLog)


        //EnrichedUser
        val enrichedUserLog = new EnrichedUser(id,numberAsWord,name,hweDeveloper,email)
//        println(s"Enriched User Received: "+enrichedUserLog.id+ " "+enrichedUserLog.numberAsWord+ " "+enrichedUserLog.name+ " "+enrichedUserLog.hweDeveloper+ " "+enrichedUserLog.email)
        /*
        OUTPUT:
Enriched User Received: 1 1 Deanna Kihn John West kihndeanna@yahoo.com
Enriched User Received: 5 5 Bartholome Spinka IV John West iv_spinka_bartholome@yahoo.com
Enriched User Received: 7 7 Afton Goyette John West goyette.afton@hotmail.com
Enriched User Received: 8 8 Edwin Lind John West lind.edwin@yahoo.com
Enriched User Received: 1 1 Alyson Kessler IV John West kessleralysoniv@gmail.com
Enriched User Received: 5 5 Miss Cortney Labadie John West cortney_labadie_miss@hotmail.com
Enriched User Received: 7 7 Giovani Wisozk John West giovani.wisozk@hotmail.com
Enriched User Received: 8 8 Roel Hintz John West hintz.roel@yahoo.com
Enriched User Received: 4 4 Vicky Wunsch II John West wunschiivicky@yahoo.com
Enriched User Received: 6 6 Flavio Klocko John West flavioklocko@hotmail.com
Enriched User Received: 10 10 Matilde Sporer John West sporermatilde@yahoo.com
Enriched User Received: 4 4 Frances Harris John West frances_harris@yahoo.com
Enriched User Received: 6 6 Morton Monahan John West monahan.morton@hotmail.com
Enriched User Received: 10 10 Karli Weimann John West karli_weimann@gmail.com
Enriched User Received: 2 2 Abelardo O'Keefe John West abelardoo'keefe@hotmail.com
Enriched User Received: 3 3 Enrique Kerluke John West kerluke.enrique@yahoo.com
Enriched User Received: 9 9 Kiley Will DDS John West will_dds_kiley@hotmail.com
Enriched User Received: 2 2 Jena Koch John West koch_jena@gmail.com
Enriched User Received: 3 3 Gail Keeling John West gailkeeling@yahoo.com
Enriched User Received: 9 9 Gerson Shields John West gerson.shields@hotmail.com
         */
//        println(s"Enriched User Received: "+enrichedUserLog)
        /*
        OUTPUT:
Enriched User Received: EnrichedUser(2,2,Abelardo O'Keefe,John West,abelardoo'keefe@hotmail.com)
Enriched User Received: EnrichedUser(3,3,Enrique Kerluke,John West,kerluke.enrique@yahoo.com)
Enriched User Received: EnrichedUser(9,9,Kiley Will DDS,John West,will_dds_kiley@hotmail.com)
Enriched User Received: EnrichedUser(2,2,Jena Koch,John West,koch_jena@gmail.com)
Enriched User Received: EnrichedUser(3,3,Gail Keeling,John West,gailkeeling@yahoo.com)
Enriched User Received: EnrichedUser(9,9,Gerson Shields,John West,gerson.shields@hotmail.com)
Enriched User Received: EnrichedUser(4,4,Vicky Wunsch II,John West,wunschiivicky@yahoo.com)
Enriched User Received: EnrichedUser(6,6,Flavio Klocko,John West,flavioklocko@hotmail.com)
Enriched User Received: EnrichedUser(10,10,Matilde Sporer,John West,sporermatilde@yahoo.com)
Enriched User Received: EnrichedUser(4,4,Frances Harris,John West,frances_harris@yahoo.com)
Enriched User Received: EnrichedUser(6,6,Morton Monahan,John West,monahan.morton@hotmail.com)
Enriched User Received: EnrichedUser(10,10,Karli Weimann,John West,karli_weimann@gmail.com)
Enriched User Received: EnrichedUser(1,1,Deanna Kihn,John West,kihndeanna@yahoo.com)
Enriched User Received: EnrichedUser(5,5,Bartholome Spinka IV,John West,iv_spinka_bartholome@yahoo.com)
Enriched User Received: EnrichedUser(7,7,Afton Goyette,John West,goyette.afton@hotmail.com)
Enriched User Received: EnrichedUser(8,8,Edwin Lind,John West,lind.edwin@yahoo.com)
Enriched User Received: EnrichedUser(1,1,Alyson Kessler IV,John West,kessleralysoniv@gmail.com)
Enriched User Received: EnrichedUser(5,5,Miss Cortney Labadie,John West,cortney_labadie_miss@hotmail.com)
Enriched User Received: EnrichedUser(7,7,Giovani Wisozk,John West,giovani.wisozk@hotmail.com)
Enriched User Received: EnrichedUser(8,8,Roel Hintz,John West,hintz.roel@yahoo.com)
         */
//        println(enrichedUserLog)

      })
    }
  }
}
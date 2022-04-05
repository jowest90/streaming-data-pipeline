# Overview

Kafka has many moving pieces, but also has a ton of helpful resources to learn available online. In this homework, your
challenge is to write answers that make sense to you, and most importantly, **in your own words!**
Two of the best skills you can get from this class are to find answers to your questions using any means possible, and to
reword confusing descriptions in a way that makes sense to you. 

### Tips
* You don't need to write novels, just write enough that you feel like you've fully answered the question
* Use the helpful resources that we post next to the questions as a starting point, but carve your own path by searching on Google, YouTube, books in a library, etc to get answers!
* We're here if you need us. Reach out anytime if you want to ask deeper questions about a topic 
* This file is a markdown file. We don't expect you to do any fancy markdown, but you're welcome to format however you like

### Your Challenge
1. Create a new branch for your answers 
2. Complete all the questions below by writing your answers under each question
3. Commit your changes and push to your forked repository

## Questions
#### What problem does Kafka help solve? Use a specific use case in your answer 
Kafka helps replace previous data querying for a better throughput, built-in partitioning, 
replication, and fault tolerance which makes it a good solution for large-scale 
message processing applications in real time.
A good use case example for this is credit fraud detection for a financial company which will 
send alerts to a person the moment a transaction has been made and see if it was authorized or not.
* Helpful resource: [Confluent Motivations and Use Cases](https://youtu.be/BsojaA1XnpM)

#### What is Kafka?
Kafka is a distributed data storage that optimizes real-time streaming data. 
* Helpful resource: [Kafka in 6 minutes](https://youtu.be/Ch5VhJzaoaI) 

#### Describe each of the following with an example of how they all fit together: 
 * Topic
   * To organize messages 
   * Example: Payments
 * Producer
   * can application that writes a topic
   * Example: writers
 * Consumer 
   * an application that reads a topic
   * Example: readers
 * Broker
   * The Kafka server
   * Example: the main server
 * Partition
   * Stores and writes new messages in its own node
   * Example: data cluster

#### Describe Kafka Producers and Consumers
producers write data to topics, and consumers read data from topics
#### How are consumers and consumer groups different in Kafka? 
While the consumer reads data from topic, the consumer group contains a cluster of consumer. 
Each consumer is exclusive to a partition.
* Helpful resource: [Consumers](https://youtu.be/lAdG16KaHLs)
* Helpful resource: [Confluent Consumer Overview](https://youtu.be/Z9g4jMQwog0)

#### How are Kafka offsets different from partitions? 
The offset is a unique ID to the partition.
#### How is data assigned to a specific partition in Kafka? 
with a key
#### Describe immutability - Is data on a Kafka topic immutable? 
Immutability means to not change. Yes, the data is immutable
#### How is data replicated across brokers in kafka? If you have a replication factor of 3 and 3 brokers, explain how data is spread across brokers
* Helpful resource [Brokers and Replication factors](https://youtu.be/ZOU7PJWZU9w)
If a broker goes down, another one will take its place as long as the replication factor is more than 1.
* if the factor is 3, B1 will have T1/P0 & T1/P2, B2 will have T1/P1 & T1/P0, and B3 will have T1/P2 & T1/P1
#### What was the most fascinating aspect of Kafka to you while learning? 
the fact that the data cannot be unchanged throughout its lifespan
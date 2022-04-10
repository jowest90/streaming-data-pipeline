# Overview

Similar to the work you did for Kafka, this is your crash course into Spark through different questions. In this homework, your
challenge is to write answers that make sense to you, and most importantly, **in your own words!**
Two of the best skills you can get from this class are to find answers to your questions using any means possible, and to
reword confusing descriptions in a way that makes sense to you. 

### Tips
* You don't need to write novels, just write enough that you feel like you've fully answered the question
* Use the helpful resources that we post next to the questions as a starting point, but carve your own path by searching on Google, YouTube, books in a library, etc to get answers!
* We're here if you need us. Reach out anytime if you want to ask deeper questions about a topic 
* This file is a markdown file. We don't expect you to do any fancy markdown, but you're welcome to format however you like
* Spark By Examples is a great resources to start with - [Spark By Examples](https://sparkbyexamples.com/)

### Your Challenge
1. Create a new branch for your answers 
2. Complete all of the questions below by writing your answers under each question
3. Commit your changes and push to your forked repository

## Questions
#### What problem does Spark help solve? Use a specific use case in your answer 
It utilizes in-memory caching, and optimized query execution for fast analytic queries against data of any size.
For example, streaming data analysis in real time.
* Helpful resource: [Apache Spark Use Cases](https://www.toptal.com/spark/introduction-to-apache-spark)
* [Overivew of Apache Spark](https://www.youtube.com/watch?v=znBa13Earms&t=42s)

#### What is Apache Spark?
An open source analytics engine for big data distribution
* Helpful resource: [Spark Overview](https://www.youtube.com/watch?v=ymtq8yjmD9I) 

#### What is distributed data processing? How does it relate to Apache Spark?  
Allows multiple computers to be used anywhere in a fair. 
In Spark, it  loads big data, do computations on it in a distributed way, and then store it.

[Apache Spark for Beginners](https://medium.com/@aristo_alex/apache-spark-for-beginners-d3b3791e259e)

#### On the physical side of a spark cluster, you have a driver and executors. Define each and give an example of how they work together to process data
* Executors start first and coordinate with the cluster manager.
They run individual tasks to return a result. 
* Drivers take the results from the executor and caches it into the worker node.
#### Define each and explain how they are different from each other 
* RDD (Resilient Distributed Dataset)
  * It is the collection of objects which is capable of storing the data partitioned across the multiple nodes of the cluster and also allows them to do processing in parallel
  * We use RDDs when we want to do low-level transformations on the dataset.
* DataFrame
  * the distributed collection of the data points, but here, the data is organized into the named columns. 
  * They allow developers to debug the code during the runtime which was not allowed with the RDDs
  * can read various file formats
  * uses a catalyst optimizer for optimization purposes 
* DataSet
  * is an extension of Dataframes API with the benefits of both RDDs and the Datasets. 
  * It is fast as well as provides a type-safe interface meaning the compiler will validate the data types of all the columns in the dataset.

#### What is a spark transformation?
results in a single or multiple new RDD’s.
Since RDD are immutable in nature, transformations always create new RDD without updating an existing one

[Spark By Examples-Transformations](https://sparkbyexamples.com/apache-spark-rdd/spark-rdd-transformations/)

#### What is a spark action? How do actions differ from transformations? 
actions are operations that return the raw values.
In other words, any RDD function that returns other than RDD is considered as an action in spark programming.
#### What is a partition in spark? Why would you ever need to repartition? 
the partition is an atomic chunk of data. 
Repartition re-distributes the data from all the partitions and this leads to full shuffle which is very expensive operation.

[Spark Partitioning](https://sparkbyexamples.com/spark/spark-repartition-vs-coalesce/)

#### What was the most fascinating aspect of Spark to you while learning? 
That Apache Spark utilizes machine learning into its data storing.
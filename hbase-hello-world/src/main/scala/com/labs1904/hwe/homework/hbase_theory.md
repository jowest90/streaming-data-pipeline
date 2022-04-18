# Overview

By now you've seen some different Big Data frameworks such as Kafka and Spark. Now we'll be focusing in on HBase. In this homework, your
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
2. Complete all of the questions below by writing your answers under each question
3. Commit your changes and push to your forked repository

## Questions
#### What is a NoSQL database? 
- NoSQL is a Non-Relational catch-all item for any database

#### In your own words, what is Apache HBase? 
- An open source distributed NoSQL database used to access big data in real time.
They don't require a schema as it uses rowKeys as an indicator.

#### What are some strengths and limitations of HBase? 
Pros:
- Scalable
- Can be developed in Java/Scala
- parallel processing supported

Cons:
- CPU and Memory intensive
- Single point of failure
- No support of transactions

* [HBase By Examples](https://sparkbyexamples.com/apache-hbase-tutorial/)

#### Explain the following concepts: 
* Rowkey
  * Optimized for scanning and allows storing related rows.
* Column Qualifier
  * the name of the column index
* Column Family
  * a group of column qualifiers
* NameSpace
  * A logical group of tables
* Table
  * A collection of rows
* Row
  * contains a KEY plus one or more columns.
* Cell
  * combination of a row, column family, and column qualifier

#### What are the differences between Get and Put commands in HBase? 
- Put inserts rows into table
- Get does the opposite of Put
* [HBase commands](https://www.tutorialspoint.com/hbase/hbase_create_data.htm)


#### What is the HBase Scan command for? 
- To view the data
* [HBase Scan](https://www.tutorialspoint.com/hbase/hbase_scan.htm)

#### What was the most interesting aspect of HBase when went through all the questions? 
- that it uses NoSQL as the foundation for this data storage.

#### What is the difference between Relational vs non-relational?
-Relational is a collection of pre-defined data items.
-Non-Relational is a catch-all item for any database. AKA NoSQL


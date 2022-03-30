package com.labs1904.hwe

object App {

  val name: String = "John"

  def main(args: Array[String]): Unit = {
//    println("Hello World")
    greeting(name)
  }

  def greeting(name: String): String = {
    "Hello " + name
  }
}
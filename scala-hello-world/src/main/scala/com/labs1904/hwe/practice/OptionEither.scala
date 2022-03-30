package com.labs1904.hwe.practice

case class Item(description: String, price: Option[Int])

case class WeatherStation(name: String, temperature: Option[Int])

object OptionEither {
  /*
   Returns age of a dog when given a human age.
   Returns None if the input is None.
 */
  def dogAge(humanAge: Option[Int]): Option[Int] = {
    if (humanAge.isEmpty) {
      None
    } else {
      humanAge.map(_ * 7)
    }
  }

  /*
    Returns the total cost af any item.
    If that item has a price, then the price + 7% of the price should be returned.
  */
  def totalCost(item: Item): Option[Double] = {
    item.price match {
      case Some(price) => Option(price + (price * .07))
      case None => None
    }
  }

  /*
    Given a list of weather temperatures, calculates the average temperature across all weather stations.
    Some weather stations don't report temperature
    Returns None if the list is empty or no weather stations contain any temperature reading.
   */
  def averageTemperature(temperatures: List[WeatherStation]): Option[Int] = {
    var sum = 0
    var numTemps = 0
    for (weatherStation <- temperatures) {
      if (weatherStation.temperature.isDefined) {
        val temp = weatherStation.temperature.get
        sum += temp
        numTemps += 1
      }
    }
    if (numTemps > 0) {
      Some(sum / numTemps)
    } else {
      None
    }
  }
}

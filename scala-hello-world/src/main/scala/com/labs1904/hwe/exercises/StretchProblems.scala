package com.labs1904.hwe.exercises
import java.util
import scala.util.control.Breaks._
object StretchProblems {

  /*
  Checks if a string is palindrome.
 */
  def isPalindrome(s: String): Boolean = {
    val len = s.length;
    for(i <- 0 until len/2) {
      if(s(i) != s(len-i-1)) return false;
    }
    return true;
  }

  /*
For a given number, return the next largest number that can be created by rearranging that number's digits.
If no larger number can be created, return -1
 */
  def getNextBiggestNumber(i: Integer): Int = {
    var result: Int = 0

    val str = String.valueOf(i)

    val ch = new Array[Char](str.length)

    for (m <- 0 until str.length) {
      ch(m) = str.charAt(m)
    }

    val n = ch.length

    var j = 0

    breakable
    {
      j = n - 1
      while ( {
        j > 0
      }) {
        if (ch(j) > ch(j - 1)) break

        j -= 1
      }
    }

    import java.util
    if (j == 0) {
      result = -1
    }
    else {
      val x = ch(j - 1)
      var min = j
      var k = j + 1
      while ( {
        k < n
      }) {
        if (ch(k) > x && ch(k) < ch(min)) min = k

        k += 1
      }
      val temp = ch(j - 1)
      ch(j - 1) = ch(min)
      ch(min) = temp
      util.Arrays.sort(ch, j, n)
      val string = String.valueOf(ch)
      val integer = string.toInt
      result = integer
    }

    result
  }

}

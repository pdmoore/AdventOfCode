package com.pdmoore.aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.core.test.TestScope
import io.kotest.matchers.shouldBe

class Day03Tests: FunSpec({
    // part 1 - wip
    // find largest two digit number on a line of length n
    // from input, find answer for each string
    // sum all two digit numbers found
    // part 2 - not started

    /*
    sample input
987654321111111
811111111111119
234234234234278
818181911112111
98 + 89 + 78 + 92 = 357
     */

    test("largest possible joltage from any two digits") {

        X.findLargestTwoDigitsFromLeft("987654321111111") shouldBe 98
        X.findLargestTwoDigitsFromLeft("811111111111119") shouldBe 89
        X.findLargestTwoDigitsFromLeft("234234234234278") shouldBe 78
        X.findLargestTwoDigitsFromLeft("818181911112111") shouldBe 92
    }


})

open class X(name: String, test: suspend TestScope.() -> Unit) {
    companion object {
        fun findLargestTwoDigitsFromLeft(input: String): Int {
            //TODO have not even started to solve the number extraction
            // it's just grab the largest digit in the string, then the largest
            // digit in the substring to the right of the first digit

            // get the largest digit & position
            val maxDigitChar = input.substring(0, input.length - 1).filter { it.isDigit() }.max()
            val indexOfFirstDigit = input.indexOf(maxDigitChar)
            val remainingString = input.substring(indexOfFirstDigit + 1)
            val secondDigitChar = remainingString.filter { it.isDigit() }.max()

            val largestDigit = maxDigitChar.digitToInt() * 10 + secondDigitChar.digitToInt()

            return largestDigit
        }
    }
}

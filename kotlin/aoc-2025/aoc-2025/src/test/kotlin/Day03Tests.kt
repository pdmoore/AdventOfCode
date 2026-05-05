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

    test("largest is the first two digits") {

        val input = "987654321111111"
        X.findLargestTwoDigitsFromLeft(input) shouldBe 98
    }




})

open class X(name: String, test: suspend TestScope.() -> Unit) {
    companion object {
        fun findLargestTwoDigitsFromLeft(input: String): Int {
            return 66
        }
    }
}

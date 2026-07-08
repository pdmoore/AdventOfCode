package com.pdmoore.aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.core.test.TestScope
import io.kotest.matchers.shouldBe

class Day03Tests: FunSpec({
    // part 1 - solved 2026-07-08
    // part 2 - not started - haven't even read description

    val sampleInput: List<String> = listOf("987654321111111",
        "811111111111119",
        "234234234234278",
        "818181911112111"
    )

    test("largest possible joltage from any two digits") {
        X.calculateJoltageAsTwoLargestDigitsOnLine("987654321111111") shouldBe 98
        X.calculateJoltageAsTwoLargestDigitsOnLine("811111111111119") shouldBe 89
        X.calculateJoltageAsTwoLargestDigitsOnLine("234234234234278") shouldBe 78
        X.calculateJoltageAsTwoLargestDigitsOnLine("818181911112111") shouldBe 92
    }

    test("part 1 answer is sum of all joltages") {
        X.solvePart1(sampleInput) shouldBe 357
    }

    test ("part 1 solution") {
        val input = PuzzleInput.asStringListFrom("./data/day03")
        X.solvePart1(input) shouldBe 17435
    }
})

open class X(name: String, test: suspend TestScope.() -> Unit) {
    companion object {
        fun calculateJoltageAsTwoLargestDigitsOnLine(input: String): Int {
            // find largest two digit number on a line of length n
            // last digit CANNOT be considered largest
            val maxDigitChar = input.substring(0, input.length - 1).filter { it.isDigit() }.max()
            val indexOfFirstDigit = input.indexOf(maxDigitChar)
            val remainingString = input.substring(indexOfFirstDigit + 1)
            val secondDigitChar = remainingString.filter { it.isDigit() }.max()

            val largestDigit = maxDigitChar.digitToInt() * 10 + secondDigitChar.digitToInt()

            return largestDigit
        }

        fun solvePart1(input: List<String>): Int {
            // for each line in input
            // calc and sum joltages

            // return total
            var total = 0
            input.forEach { input -> total += calculateJoltageAsTwoLargestDigitsOnLine(input) }
            return total
        }
    }
}

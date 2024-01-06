package com.pdmoore.aoc

import com.pdmoore.aoc.Day03.Companion.DO_NOT_USE_ROBO_SANTA
import com.pdmoore.aoc.Day03.Companion.USE_ROBO_SANTA
import com.pdmoore.aoc.Day03.Companion.countHousesVisited
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

class Day03Tests : FunSpec( {

    test("Single move delivers to two unique locations") {
        val input = ">"
        countHousesVisited(input, DO_NOT_USE_ROBO_SANTA) shouldBe 2
    }

    test("Non-repeating sequence delivers to each location") {
        val input = "^>v<"
        countHousesVisited(input, DO_NOT_USE_ROBO_SANTA) shouldBe 4
    }

    test("Repeating sequence only counts unique houses") {
        val input = "^v^v^v^v^v"
        countHousesVisited(input, DO_NOT_USE_ROBO_SANTA) shouldBe 2
    }

    test("part 1 solution") {
        val input = PuzzleInput.asSingleLine("./data/day03")
        countHousesVisited(input, DO_NOT_USE_ROBO_SANTA) shouldBe 2572
    }

    test("Robo-Santa alternates moves with real Santa - simple example") {
        val input = "^v"
        countHousesVisited(input, USE_ROBO_SANTA) shouldBe 3
    }

    data class InputAndExpected(val inputLine: String, val expected: Int)
    context("Santa and Robo-Santa alternate executing moves") {
        withData(
            InputAndExpected("^v", 3),
            InputAndExpected("^>v<", 3),
            InputAndExpected("^v^v^v^v^v", 11)
        ) { (input, expected) ->
            countHousesVisited(input, USE_ROBO_SANTA) shouldBe expected
        }
    }

    test("part 2 solution") {
        val input = PuzzleInput.asSingleLine("./data/day03")
        countHousesVisited(input, USE_ROBO_SANTA) shouldBe 2631
    }
})


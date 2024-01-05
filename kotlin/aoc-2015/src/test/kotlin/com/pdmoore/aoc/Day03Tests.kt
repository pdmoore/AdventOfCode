package com.pdmoore.aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

class Day03Tests : FunSpec( {

    test("Single move delivers to two unique locations") {
        val input = ">"
        Day03.houseCountSantaDeliversTo(input) shouldBe 2
    }

    test("Non-repeating sequence delivers to each location") {
        val input = "^>v<"
        Day03.houseCountSantaDeliversTo(input) shouldBe 4
    }

    test("Repeating sequence only counts unique houses") {
        val input = "^v^v^v^v^v"
        Day03.houseCountSantaDeliversTo(input) shouldBe 2
    }

    test("part 1 solution") {
        val input = PuzzleInput.asSingleLine("./data/day03")
        Day03.houseCountSantaDeliversTo(input) shouldBe 2572
    }

    test("Robo-Santa alternates moves with real Santa - simple example") {
        val input = "^v"
        Day03.houseCountWithRoboSantaDeliveries(input) shouldBe 3
    }

    data class InputAndExpected(val inputLine: String, val expected: Int)
    context("Santa and Robo-Santa alternate executing moves") {
        withData(
            InputAndExpected("^v", 3),
            InputAndExpected("^>v<", 3),
            InputAndExpected("^v^v^v^v^v", 11)
        ) { (input, expected) ->
            Day03.houseCountWithRoboSantaDeliveries(input) shouldBe expected
        }
    }

    test("part 2 solution") {
        val input = PuzzleInput.asSingleLine("./data/day03")
        Day03.houseCountWithRoboSantaDeliveries(input) shouldBe 2631
    }
})


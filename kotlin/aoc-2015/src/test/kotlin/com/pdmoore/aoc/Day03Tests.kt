package com.pdmoore.aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day03Tests : FunSpec( {


    // input comes as single very long string

    test("Single move delivers to two unique locations") {
        val input = ">"
        Day03.housesThatReceiveAtLeastOnePresent(input) shouldBe 2
    }

    test("Non-repeating sequence delivers to each location") {
        val input = "^>v<"
        Day03.housesThatReceiveAtLeastOnePresent(input) shouldBe 4
    }

    test("Repeating sequence only counts unique houses") {
        val input = "^v^v^v^v^v"
        Day03.housesThatReceiveAtLeastOnePresent(input) shouldBe 2
    }

    test("part 1 solution") {
        val input = PuzzleInput.asSingleLine("./data/day03")
        Day03.housesThatReceiveAtLeastOnePresent(input) shouldBe 2572
    }
})


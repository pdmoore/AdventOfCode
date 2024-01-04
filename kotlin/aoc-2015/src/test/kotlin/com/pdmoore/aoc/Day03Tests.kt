package com.pdmoore.aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day03Tests : FunSpec( {


    // input comes as single very long string

    // basically keep a set of unique points visited
    // current location goes up/down/left/right per ^v<>

    test("Single move delivers to two unique locations") {
        val input = ">"
        Day03.housesThatReceiveAtLeastOnePresent(input) shouldBe 2
    }

    test("Non-repeating sequence delivers to each location") {
        val input = "^>v<"
        Day03.housesThatReceiveAtLeastOnePresent(input) shouldBe 4
    }
})


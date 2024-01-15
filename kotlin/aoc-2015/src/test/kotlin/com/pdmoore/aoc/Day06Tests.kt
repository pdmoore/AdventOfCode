package com.pdmoore.aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day06Tests: FunSpec( {

    // how to track lit lights?
    // grid off all locations or just Coord of lit ones?
    // turn on command
    // turn off command
    // toggle command
    // Part 1 loop to solve List<String> input
    test("Turn on command will turn on all lights in the rectangle") {
        val input = "turn on 0,0 through 2,2"

        var sut = Day06(1000, 1000)
        sut.followInstruction(input)
        sut.countOfLitLights() shouldBe 9

    }




})
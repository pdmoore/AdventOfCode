package com.pdmoore.aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day06Tests: FunSpec( {

    test("Turn on command will turn on all lights in the rectangle") {
        val input = "turn on 0,0 through 2,2"

        val sut = Day06()
        sut.followInstructionUsingSet(input)
        sut.countOfLitLightsUsingSet() shouldBe 9
    }

    test("Turn on command will turn on all lights in the rectangle - 2D int array impl") {
        val input = "turn on 0,0 through 2,2"

        val sut = Day06()
        sut.followInstructionUsingArray(input)
        sut.countOfLitLightsUsingArray() shouldBe 9
    }

    test("Turn off command will turn off all lights in the rectangle") {
        val turnOn = "turn on 0,0 through 2,2"
        val turnOff = "turn off 0,0 through 2,2"

        val sut = Day06()
        sut.followInstructionUsingSet(turnOn)
        sut.followInstructionUsingSet(turnOff)
        sut.countOfLitLightsUsingSet() shouldBe 0
    }

    test("Turn off command will turn off all lights in the rectangle - 2D array impl") {
        val turnOn = "turn on 0,0 through 2,2"
        val turnOff = "turn off 0,0 through 2,2"

        val sut = Day06()
        sut.followInstructionUsingArray(turnOn)
        sut.followInstructionUsingArray(turnOff)
        sut.countOfLitLightsUsingArray() shouldBe 0
    }

    test("Toggle command will turn lights on that are off") {
        val toggleOn = "toggle 0,0 through 2,2"

        val sut = Day06()
        sut.followInstructionUsingSet(toggleOn)
        sut.countOfLitLightsUsingSet() shouldBe 9
    }

    test("Toggle command will turn lights on that are off - 2D array impl") {
        val toggleOn = "toggle 0,0 through 2,2"

        val sut = Day06()
        sut.followInstructionUsingArray(toggleOn)
        sut.countOfLitLightsUsingArray() shouldBe 9
    }

    test("Toggle command will turn lights off that are on") {
        val turnOn = "turn on 0,0 through 2,2"
        val toggleOff = "toggle 0,0 through 2,2"

        val sut = Day06()
        sut.followInstructionUsingSet(turnOn)
        sut.followInstructionUsingSet(toggleOff)
        sut.countOfLitLightsUsingSet() shouldBe 0
    }

    test("Toggle command will turn lights off that are on - 2D array impl") {
        val turnOn = "turn on 0,0 through 2,2"
        val toggleOff = "toggle 0,0 through 2,2"

        val sut = Day06()
        sut.followInstructionUsingArray(turnOn)
        sut.followInstructionUsingArray(toggleOff)
        sut.countOfLitLightsUsingArray() shouldBe 0
    }

    test("Part 1 solved using a Set takes 7 seconds") {
        val input = PuzzleInput.asListOfStrings("./data/day06")
        val sut = Day06()

        sut.followInstructionsUsingSet(input)

        sut.countOfLitLightsUsingSet() shouldBe 377891
    }

    test("Part 1 solved using 2D Array takes less than 1/2 second") {
        val input = PuzzleInput.asListOfStrings("./data/day06")
        val sut = Day06()

        sut.followInstructionsUsingArray(input)

        sut.countOfLitLightsUsingArray() shouldBe 377891
    }
})
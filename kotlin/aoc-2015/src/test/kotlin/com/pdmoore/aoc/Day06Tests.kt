package com.pdmoore.aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day06Tests: FunSpec( {

    // how to track lit lights?
    // grid off all locations or just Coord of lit ones?
    // DONE turn on command
    // DONE turn off command
    // toggle command when off to on
    // toggle command when on to off
    // Part 1 loop to solve List<String> input
    test("Turn on command will turn on all lights in the rectangle") {
        val input = "turn on 0,0 through 2,2"

        var sut = Day06(1000, 1000)
        sut.followInstructionUsingSet(input)
        sut.countOfLitLightsUsingSet() shouldBe 9
    }

    test("Turn on command will turn on all lights in the rectangle - 2D int array impl") {
        val input = "turn on 0,0 through 2,2"

        var sut = Day06(1000, 1000)
        sut.followInstructionUsingArray(input)
        sut.countOfLitLightsUsingArray() shouldBe 9
    }

    test("Turn off comand will turn off all lights in the rectangle") {
        val turnOn = "turn on 0,0 through 2,2"
        val turnOff = "turn off 0,0 through 2,2"

        var sut = Day06(1000, 1000)
        sut.followInstructionUsingSet(turnOn)
        sut.followInstructionUsingSet(turnOff)
        sut.countOfLitLightsUsingSet() shouldBe 0
    }

    test("Turn off comand will turn off all lights in the rectangle - 2D array impl") {
        val turnOn = "turn on 0,0 through 2,2"
        val turnOff = "turn off 0,0 through 2,2"

        var sut = Day06(1000, 1000)
        sut.followInstructionUsingArray(turnOn)
        sut.followInstructionUsingArray(turnOff)
        sut.countOfLitLightsUsingArray() shouldBe 0
    }

    test("Toggle command will turn lights on that are off") {
        val toggleOn = "toggle 0,0 through 2,2"

        var sut = Day06(1000, 1000)
        sut.followInstructionUsingSet(toggleOn)
        sut.countOfLitLightsUsingSet() shouldBe 9
    }

    test("Toggle command will turn lights on that are off - 2D array impl") {
        val toggleOn = "toggle 0,0 through 2,2"

        var sut = Day06(1000, 1000)
        sut.followInstructionUsingArray(toggleOn)
        sut.countOfLitLightsUsingArray() shouldBe 9
    }

    test("Toggle command will turn lights off that are on") {
        val turnOn = "turn on 0,0 through 2,2"
        val toggleOff = "toggle 0,0 through 2,2"

        var sut = Day06(1000, 1000)
        sut.followInstructionUsingSet(turnOn)
        sut.followInstructionUsingSet(toggleOff)
        sut.countOfLitLightsUsingSet() shouldBe 0
    }

    test("Toggle command will turn lights off that are on - 2D array impl") {
        val turnOn = "turn on 0,0 through 2,2"
        val toggleOff = "toggle 0,0 through 2,2"

        var sut = Day06(1000, 1000)
        sut.followInstructionUsingArray(turnOn)
        sut.followInstructionUsingArray(toggleOff)
        sut.countOfLitLightsUsingArray() shouldBe 0
    }

    test("Part 1 solved using a Set takes 7 seconds") {
        val input = PuzzleInput.asListOfStrings("./data/day06")
        var sut = Day06(1000, 1000)

        sut.followInstructionsUsingSet(input)

        sut.countOfLitLightsUsingArray() shouldBe 377891
    }

    test("Part 1 solved using 2D Array takes less than 1/2 second") {
        val input = PuzzleInput.asListOfStrings("./data/day06")
        var sut = Day06(1000, 1000)

        sut.followInstructionsUsingArray(input)

        sut.countOfLitLightsUsingArray() shouldBe 377891
    }
})
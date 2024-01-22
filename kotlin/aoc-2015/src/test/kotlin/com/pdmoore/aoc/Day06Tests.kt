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

        val sut = Day06.ProcessVia2DArray()
        sut.processSingleInstruction(input)
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

        val sut = Day06.ProcessVia2DArray(listOf<String>())
        sut.processSingleInstruction(turnOn)
        sut.processSingleInstruction(turnOff)
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

        val sut = Day06.ProcessVia2DArray()
        sut.processSingleInstruction(toggleOn)

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

        val sut = Day06.ProcessVia2DArray()
        sut.processSingleInstruction(turnOn)
        sut.processSingleInstruction(toggleOff)
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
        val sut = Day06.ProcessVia2DArray(input)

        sut.processAllInstructions()

        sut.countOfLitLightsUsingArray() shouldBe 377891
    }

    test("Part2 example turn on should increase brightness of one light to 1") {
        val turnOnOneLight = "turn on 0,0 through 0,0"

        val sut = Day06.ProcessViaBrightness()
        sut.processSingleInstruction(turnOnOneLight)
        sut.countOfBrightness() shouldBe 1
    }

    test("Part2 example turn has a floor of 0") {
        val turnOff = "turn off 0,0 through 2,2"

        val sut = Day06.ProcessViaBrightness()
        sut.processSingleInstruction(turnOff)
        sut.processSingleInstruction(turnOff)
        sut.countOfBrightness() shouldBe 0
    }

    test("Part2 example toggle should increase brightness of all by 2") {
        val toggle = "toggle 0,0 through 999,999"

        val sut = Day06.ProcessViaBrightness()
        sut.processSingleInstruction(toggle)
        sut.countOfBrightness() shouldBe 2_000_000
    }

    test("Part 2 solved using 2D Array") {
        val input = PuzzleInput.asListOfStrings("./data/day06")
        val sut = Day06.ProcessViaBrightness(input)

        sut.processAllInstructions()

        sut.countOfBrightness() shouldBe 14_110_788
    }
})
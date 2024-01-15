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
        sut.followInstruction(input)
        sut.countOfLitLights() shouldBe 9
    }

    test("Turn off comand will turn off all lights in the rectangle") {
        val turnOn = "turn on 0,0 through 2,2"
        val turnOff = "turn off 0,0 through 2,2"

        var sut = Day06(1000, 1000)
        sut.followInstruction(turnOn)
        sut.followInstruction(turnOff)
        sut.countOfLitLights() shouldBe 0
    }

    test("Toggle command will turn lights on that are off") {
        val toggleOn = "toggle 0,0 through 2,2"

        var sut = Day06(1000, 1000)
        sut.followInstruction(toggleOn)
        sut.countOfLitLights() shouldBe 9
    }

    test("Toggle command will turn lights off that are on") {
        val turnOn = "turn on 0,0 through 2,2"
        val toggleOff = "toggle 0,0 through 2,2"

        var sut = Day06(1000, 1000)
        sut.followInstruction(turnOn)
        sut.followInstruction(toggleOff)
        sut.countOfLitLights() shouldBe 0
    }

    test("Part 1 solved") {
        val input = PuzzleInput.asListOfStrings("./data/day06")
        var sut = Day06(1000, 1000)

        sut.followInstructions(input)

        sut.countOfLitLights() shouldBe 377891
    }


})
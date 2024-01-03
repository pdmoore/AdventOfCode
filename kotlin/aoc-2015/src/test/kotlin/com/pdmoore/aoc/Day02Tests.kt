package com.pdmoore.aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

class Day02Tests : FunSpec( {

    test("Surface area is 2*l*w + 2*w*h + 2*h*l") {
        val inputLine = "2x3x4"
        Day02.surfaceAreaOf(inputLine) shouldBe 52
    }

    test("Smallest side of l*w*h rectangle") {
        val inputLine = "2x3x4"
        Day02.areaOfSmallestSide(inputLine) shouldBe 6
    }

    data class SideLengths(val inputLine: String, val expected: Int)
    context("Wrapping paper needed is surface area plus area of smallest side") {
        withData(
            SideLengths("2x3x4", 58),
            SideLengths("1x1x10", 43)
        ) { (inputLine, expected) ->
            Day02.solvePart21(inputLine) shouldBe expected
        }
    }

    test("Solve part 1") {
        val input = PuzzleInput.asListOfStrings("./data/day02")
        Day02.solvePart21(input) shouldBe 1586300
    }

    test("Volume is l*w*h") {
        Day02.volumeOf("2x3x4") shouldBe 24
        Day02.volumeOf("1x1x10") shouldBe 10
    }

    context("Smallest perimeter of any face") {
        withData(
            SideLengths("2x3x4", 10),
            SideLengths("1x1x10", 4)
        ) { (inputLine, expected) ->
            Day02.smallestPerimeterOf(inputLine) shouldBe expected
        }
    }

    context("Amount of ribbon needed is smallest perimeter plus volume") {
        withData(
            SideLengths("2x3x4", 34),
            SideLengths("1x1x10", 14)
        ) { (inputLine, expected) ->
            Day02.solvePart2(inputLine) shouldBe expected
        }
    }

    test("Solve part 2") {
        val input = PuzzleInput.asListOfStrings("./data/day02")
        Day02.solvePart2(input) shouldBe 3737498
    }
})


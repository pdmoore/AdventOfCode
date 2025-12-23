package com.pdmoore.aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day01Tests : FunSpec ({

    val sampleInput: List<String> = listOf("L68",
        "L30",
        "R48",
        "L5",
        "R60",
        "L55",
        "L1",
        "L99",
        "R14",
        "L82"
    )

    test("Right hand rotation, no wrap around") {
        Dial(50).rotate("R11").position shouldBe 61
    }

    test("Left rotation, no wrap around") {
        Dial(50).rotate("L11").position shouldBe 39
    }

    test("Left rotation, wrap around") {
        Dial(5).rotate("L10").position shouldBe 95
    }

    test("Right rotation, wrap around") {
        Dial(95).rotate("R10").position shouldBe 5
        Dial(95).rotate("R5").position shouldBe 0
    }

    test("Multiple rotations, example, confirm final position") {
        Dial.rotate(sampleInput).position shouldBe 32
    }

    // rotations can exceed 100, need to count number of times i tlands on zero?



    test("part 1, example") {
        Day01(sampleInput).solve() shouldBe 3
    }

    test("part 1 solution") {
        val input = PuzzleInput.asStringListFrom("./data/day01")
        Day01(input).solve() shouldBe 1055
    }
})


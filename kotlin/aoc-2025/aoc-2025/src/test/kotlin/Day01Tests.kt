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

    test("Left rotation, exceeds 100") {
        Dial(50).rotate("L350").position shouldBe 0
    }

    test("Right rotation, exceeds 100") {
        Dial(50).rotate("R250").position shouldBe 0
    }

    test("part 2 - left rotation that passes 0 once") {
        Dial(14).rotate("L82").pointingAtZeroCount shouldBe 1
    }

    test("part 2 - right rotation that passes 0 once") {
        Dial(95).rotate("R60").pointingAtZeroCount shouldBe 1
    }

    test("part 2 - right rotation lands on 0") {
        Dial(52).rotate("R48").pointingAtZeroCount shouldBe 1
    }

    test("part 2 - right rotation past 0 twice, then lands on 0") {
        Dial(52).rotate("R248").pointingAtZeroCount shouldBe 3
    }

    test("part 2 - left rotation lands on 0") {
        Dial(55).rotate("L55").pointingAtZeroCount shouldBe 1
    }

    test("part 2 - left rotation past 0 one, then lands on 0") {
        Dial(55).rotate("L155").pointingAtZeroCount shouldBe 2
    }

    test("part 2 - start at 0, don't count it twice") {
        Dial(0).rotate("L5").pointingAtZeroCount shouldBe 0
    }

    test("part 2 last example") {
        Dial(50).rotate("R1000").pointingAtZeroCount shouldBe 10
    }

    // TODO - double check the inputs, then debug the code
    // feels like this will still be wrong in the day01 input
    test("part 2 - reddit examples") {
        val d = Dial(50).rotate("R50")
        d.rotate("L100").pointingAtZeroCount shouldBe 2
        val d2 = Dial(50).rotate("L50")
        d2.rotate("L100").pointingAtZeroCount shouldBe 2
    }

    test("part 2 - reddit examples as list") {
        val input = listOf("R50", "L100")
        Day01(input).solvePart2() shouldBe 2
    }

    test("part 1, example") {
        Day01(sampleInput).solve() shouldBe 3
    }

    test("part 1 solution") {
        val input = PuzzleInput.asStringListFrom("./data/day01")
        Day01(input).solve() shouldBe 1055
    }

    test("part 2, example") {
        Day01(sampleInput).solvePart2() shouldBe 6
    }

    test("part 2 solution") {
        val input = PuzzleInput.asStringListFrom("./data/day01")
        Day01(input).solvePart2() shouldBe 6386
    }
})


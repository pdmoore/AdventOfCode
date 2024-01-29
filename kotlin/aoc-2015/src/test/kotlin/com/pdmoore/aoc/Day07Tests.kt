package com.pdmoore.aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day07Tests: FunSpec( {

    /*
DONE    signal to ID    123 -> x
DONE    AND gate        x AND y -> d
DONE    OR gate         x OR y -> e
DONE    LSHIFT gate     x LSHIFT 2 -> f
DONE    RSHIFT gate     y RSHIFT 2 -> g
    NOT gate        NOT x -> h

    example final states
d: 72
e: 507
f: 492
g: 114
h: 65412
i: 65079
x: 123
y: 456
     */

    test("signal to ID simply assigns a value to an identifier") {
        val input = "123 -> x"

        val sut = Day07()
        sut.processInputLine(input)

        sut.valueOf("x") shouldBe 123
    }

    test("AND gate") {
        val input = listOf("123 -> x", "456 -> y", "x AND y -> d")

        val sut = Day07()
        sut.processInput(input)

        sut.valueOf("d") shouldBe 72
    }

    test("OR gate") {
        val input = listOf("123 -> x", "456 -> y", "x OR y -> e")

        val sut = Day07()
        sut.processInput(input)

        sut.valueOf("e") shouldBe 507

    }

    test("NOT gate") {
        val input = listOf("123 -> x", "NOT x -> h")

        val sut = Day07()
        sut.processInput(input)

        sut.valueOf("h") shouldBe 65412
    }

    test("LSHIFT gate") {
        val input = listOf("123 -> x", "x LSHIFT 2 -> f")

        val sut = Day07()
        sut.processInput(input)

        sut.valueOf("f") shouldBe 492
    }

    test("RSHIFT gate") {
        val input = listOf("456 -> y", "y RSHIFT 2 -> g")

        val sut = Day07()
        sut.processInput(input)

        sut.valueOf("g") shouldBe 114
    }
})
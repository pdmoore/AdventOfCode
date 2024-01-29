package com.pdmoore.aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day07Tests: FunSpec( {

    /*
DONE    signal to ID    123 -> x
    AND gate        x AND y -> d
    OR gate         x OR y -> e
    LSHIFT gate     x LSHIFT 2 -> f
    RSHIFT gate     y RSHIFT 2 -> g
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
        val inAssignX = "123 -> x"
        val inAssignY = "456 -> y"
        val input = "x AND y -> d"

        val sut = Day07()
        sut.processInputLine(inAssignX)
        sut.processInputLine(inAssignY)
        sut.processInputLine(input)

        sut.valueOf("d") shouldBe 72

    }

})
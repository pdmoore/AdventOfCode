package com.pdmoore.aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

const val PUZZLE_INPUT = "ckczppom"

class Day04Tests : FunSpec( {


    // MD5 hash, without hex
    // with hex
    // find via searching, from 0 on up (or backwards?)
    // secret key (puzzle input)
    // hexadecimal hash
    // result has 5 leading zeros
    // start with 00000_000000 and see if it matches secret?

    test("Generate MD5 hash given a key and value") {
        val hashed = Day04.generateMD5("abcdef609043")
        val firstN = hashed.substring(0, 11)
        firstN shouldBe "000001dbbfa"
    }

    test("Generate MD5 hash given a different key and value") {
        val hashed = Day04.generateMD5("pqrstuv1048970")
        val firstN = hashed.substring(0, 11)
        firstN shouldBe "000006136ef"
    }

    test("Can tell a given secret + number starts with 5 zeros") {
        Day04.startsWithZeros(Day04.generateMD5("abcdef609043"), 5) shouldBe true
        Day04.startsWithZeros(Day04.generateMD5("pqrstuv1048970"), 5) shouldBe true
    }

    test("Can tell a given secret + number does not start with 5 zeros") {
        Day04.startsWithZeros(Day04.generateMD5("abcdef609403"), 5) shouldBe false
        Day04.startsWithZeros(Day04.generateMD5("pqrstuv1048790"), 5) shouldBe false
    }

    test("Part 1 - find lowest number that hashes to start with 5 zeros") {
        val actual = Day04.solvePart1(PUZZLE_INPUT)
        actual shouldBe 117946
    }

    // TODO - takes about 3 seconds - kotlin multicore?
    test("Part 2 - find lowest number that hashes to start with 6 zeros") {
        val actual = Day04.solvePart2(PUZZLE_INPUT)
        actual shouldBe 3938038
    }


})
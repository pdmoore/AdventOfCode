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




})
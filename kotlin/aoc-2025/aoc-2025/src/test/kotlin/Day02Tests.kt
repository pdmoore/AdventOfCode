package com.pdmoore.aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.math.BigInteger

class Day02Tests: FunSpec ( {

    val sampleInput = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,\n" +
            "1698522-1698528,446443-446449,38593856-38593862,565653-565659,\n" +
            "824824821-824824827,2121212118-2121212124"


    test("split the input") {
        sampleInput.split(",").count() shouldBe 11
    }

    test("isInvalid signature - returns true when valid") {
        val sut = Thing()
        sut.isInvalid("1") shouldBe false
    }

    test("isInvalid - same digit repeated twice") {
        val sut = Thing()
        sut.isInvalid("55") shouldBe true
    }

    test("isInvalid - two digits repeated twice") {
        val sut = Thing()
        sut.isInvalid("6464") shouldBe true
    }

    test("isInvalid - remaining examples of invalid IDs") {
        val sut = Thing()
        sut.isInvalid("123123") shouldBe true
        sut.isInvalid("1188511885") shouldBe true
        sut.isInvalid("222222") shouldBe true
    }

    // pass thing all the input
    // split the input
    // for each pair in the input
    // convert to BigInteger and loop through all
    // collect invalid inputs
    // sum invalid inputs

    test("sum IDs") {
        val invalidIDs = listOf(BigInteger("11"), BigInteger("22"), BigInteger("99"),
                        BigInteger("1010"), BigInteger("1188511885"), BigInteger("222222"),
            BigInteger("446446"), BigInteger("38593859"))

        val sut = Thing()
        sut.sum(invalidIDs) shouldBe BigInteger("1227775554")
    }


})

open class Thing {
    fun isInvalid(id: String): Boolean {
        // can detect repeat based on splitting string in half?
        val middle = id.length / 2
        val firstHalf = id.substring(0, middle)
        val secondHalf = id.substring(middle)

        return firstHalf == secondHalf
    }

    fun sum(invalidIDs: List<BigInteger>): BigInteger {
        return invalidIDs.sumOf { it }
    }

}

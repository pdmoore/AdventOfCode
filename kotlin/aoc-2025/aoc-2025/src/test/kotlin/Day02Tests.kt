package com.pdmoore.aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertAll
import java.math.BigInteger

class Day02Tests: FunSpec ( {

    // part 1 solved
    // Refactor part 1
    // tackle part 2

    val sampleInput = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224," +
            "1698522-1698528,446443-446449,38593856-38593862,565653-565659," +
            "824824821-824824827,2121212118-2121212124"

    test("split the input") {
        sampleInput.split(",").count() shouldBe 11
    }

    test("isInvalid signature - returns true when valid") {
        val sut = Thing()
        sut.isInvalid(BigInteger("1")) shouldBe false
    }

    test("isInvalid - same digit repeated twice") {
        val sut = Thing()
        sut.isInvalid(BigInteger("55")) shouldBe true
    }

    test("isInvalid - two digits repeated twice") {
        val sut = Thing()
        sut.isInvalid(BigInteger("6464")) shouldBe true
    }

    test("find invalid IDs in range") {
        val input = "11-22"
        val sut = Thing()
        val invalidIDs = sut.findAllInvalidIdsIn(input)
        assertAll(
            {invalidIDs.size shouldBe 2},
            {invalidIDs.contains(BigInteger("11")) shouldBe true},
            {invalidIDs.contains(BigInteger("22")) shouldBe true}
        )
    }

    test("find invalid IDs in range, when no invalid IDs exist") {
        val input = "1698522-1698528"
        val sut = Thing()
        val invalidIDs = sut.findAllInvalidIdsIn(input)
        invalidIDs.size shouldBe 0
    }

    test("find invalid IDs when more than one range") {
        val input = "11-22,95-115"
        val sut = Thing()
        val invalidIDs = sut.findAllInvalidIdsIn(input)
        invalidIDs.size shouldBe listOf(11, 22, 99).count()
    }

    test("isInvalid - remaining examples of invalid IDs") {
        val sut = Thing()
        sut.isInvalid(BigInteger("123123")) shouldBe true
        sut.isInvalid(BigInteger("1188511885")) shouldBe true
        sut.isInvalid(BigInteger("222222")) shouldBe true
    }

    test("sum IDs") {
        val invalidIDs = listOf(BigInteger("11"), BigInteger("22"), BigInteger("99"),
                        BigInteger("1010"), BigInteger("1188511885"), BigInteger("222222"),
            BigInteger("446446"), BigInteger("38593859"))

        val sut = Thing()
        sut.sum(invalidIDs) shouldBe BigInteger("1227775554")
    }

    test("solve part 1 example") {
        val sut = Thing()
        sut.solve(sampleInput) shouldBe BigInteger("1227775554")
    }

    test("solve part 1") {
        val sut = Thing()
        sut.solve(PuzzleInput.asStringFrom("./data/day02")) shouldBe BigInteger("30599400849")
    }
})

open class Thing {
    fun sum(invalidIDs: List<BigInteger>): BigInteger {
        return invalidIDs.sumOf { it }
    }

    fun findAllInvalidIdsIn(range: String): MutableList<BigInteger> {
        val allRanges: MutableList<String> = mutableListOf()
        if (range.contains(",")) {
            allRanges.addAll(range.split(","))
        } else {
            allRanges.add(range)
        }

        val invalidIDs: MutableList<BigInteger> = mutableListOf()
        for (range in allRanges) {
            invalidIDs.addAll(findAllInvalidIdsInSingleRange(range))
        }
        return invalidIDs
    }

    private fun findAllInvalidIdsInSingleRange(range: String): Collection<BigInteger> {
        val invalidIDs: MutableList<BigInteger> = mutableListOf()
        val split = range.split("-")
        var currentIDbeingChecked = BigInteger(split[0])
        val upperLimitID = BigInteger(split[1])
        while (currentIDbeingChecked <= upperLimitID) {
            when {
                isInvalid(currentIDbeingChecked) -> invalidIDs.add(currentIDbeingChecked)
            }
            currentIDbeingChecked = currentIDbeingChecked.add(BigInteger.ONE)
        }
        return invalidIDs
    }

    fun isInvalid(id: BigInteger): Boolean {
        return isInvalid(id.toString())
    }
    private fun isInvalid(id: String): Boolean {
        val middle = id.length / 2
        val firstHalf = id.substring(0, middle)
        val secondHalf = id.substring(middle)
        return firstHalf == secondHalf
    }

    fun solve(listOfRanges: String): BigInteger {
        val invalidIDsInAllRanges = findAllInvalidIdsIn(listOfRanges)
        return sum(invalidIDsInAllRanges)
    }
}

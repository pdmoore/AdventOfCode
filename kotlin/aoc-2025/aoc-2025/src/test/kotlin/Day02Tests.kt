package com.pdmoore.aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertAll
import java.math.BigInteger

class Day02Tests : FunSpec({

    // part 1 solved
    // part 2 solved
    // refactor - part 2 was a lot of copy/paste to get the answer
    // try to pass the function that determines if an ID is invalid to the looping functions

    val sampleInput = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224," +
            "1698522-1698528,446443-446449,38593856-38593862,565653-565659," +
            "824824821-824824827,2121212118-2121212124"

    test("split the input") {
        sampleInput.split(",").count() shouldBe 11
    }

    test("isInvalid signature - returns true when valid") {
        val sut = Thing()
        sut.idRepeatsTwice(BigInteger("1")) shouldBe false
    }

    test("isInvalid - same digit repeated twice") {
        val sut = Thing()
        sut.idRepeatsTwice(BigInteger("55")) shouldBe true
    }

    test("isInvalid - two digits repeated twice") {
        val sut = Thing()
        sut.idRepeatsTwice(BigInteger("6464")) shouldBe true
    }

    test("find invalid IDs in range") {
        val input = "11-22"
        val sut = Thing()
        val invalidIDs = sut.findAllInvalidsIn(input, 1)
        assertAll(
            { invalidIDs.size shouldBe 2 },
            { invalidIDs.contains(BigInteger("11")) shouldBe true },
            { invalidIDs.contains(BigInteger("22")) shouldBe true }
        )
    }

    test("find invalid IDs in range, when no invalid IDs exist") {
        val input = "1698522-1698528"
        val sut = Thing()
        val invalidIDs = sut.findAllInvalidsIn(input, 1)
        invalidIDs.size shouldBe 0
    }

    test("find invalid IDs when more than one range") {
        val input = "11-22,95-115"
        val sut = Thing()
        val invalidIDs = sut.findAllInvalidsIn(input, 1)
        invalidIDs.size shouldBe listOf(11, 22, 99).count()
    }

    test("isInvalid - remaining examples of invalid IDs") {
        val sut = Thing()
        sut.idRepeatsTwice(BigInteger("123123")) shouldBe true
        sut.idRepeatsTwice(BigInteger("1188511885")) shouldBe true
        sut.idRepeatsTwice(BigInteger("222222")) shouldBe true
        sut.idRepeatsTwice(BigInteger("111")) shouldBe false
    }

    test("sum IDs") {
        val invalidIDs = listOf(
            BigInteger("11"), BigInteger("22"), BigInteger("99"),
            BigInteger("1010"), BigInteger("1188511885"), BigInteger("222222"),
            BigInteger("446446"), BigInteger("38593859")
        )

        val sut = Thing()
        sut.sum(invalidIDs) shouldBe BigInteger("1227775554")
    }

    test("detect patterns that repeat at least twice - part 2") {
        val sut = Thing()
        assertAll(
            { sut.idRepeatsAtLeastTwice(BigInteger("1111111")) shouldBe true },
            { sut.idRepeatsAtLeastTwice(BigInteger("1212121212")) shouldBe true },
            { sut.idRepeatsAtLeastTwice(BigInteger("123123123")) shouldBe true },
            { sut.idRepeatsAtLeastTwice(BigInteger("12341234")) shouldBe true }
        )
    }

    test("solve part 1 example") {
        val sut = Thing()
        sut.solvePart1(sampleInput) shouldBe BigInteger("1227775554")
    }

    test("solve part 1") {
        val sut = Thing()
        sut.solvePart1(PuzzleInput.asStringFrom("./data/day02")) shouldBe BigInteger("30599400849")
    }

    test("solve part 2 example") {
        val sut = Thing()
        sut.solvePart2(sampleInput) shouldBe BigInteger("4174379265")
    }

    test("solve part 2") {
        val sut = Thing()
        sut.solvePart2(PuzzleInput.asStringFrom("./data/day02")) shouldBe BigInteger("46270373595")
    }
})

open class Thing {
    fun solvePart1(listOfRanges: String): BigInteger {
        val invalidIDsInAllRanges = findAllInvalidsIn(listOfRanges, 1)
        return sum(invalidIDsInAllRanges)
    }

    fun solvePart2(listOfRanges: String): BigInteger {
        val invalidIDsInAllRanges = findAllInvalidsIn(listOfRanges, 2)
        return sum(invalidIDsInAllRanges)
    }


    fun findAllInvalidsIn(range: String, partNumber: Int): MutableList<BigInteger> {
        val allRanges: MutableList<String> = mutableListOf()
        if (range.contains(",")) {
            allRanges.addAll(range.split(","))
        } else {
            allRanges.add(range)
        }

        val invalidIDs: MutableList<BigInteger> = mutableListOf()
        for (range in allRanges) {
            if (partNumber == 1) {
                invalidIDs.addAll(findAllInvalidIdsInSingleRange(range))
            } else {
                invalidIDs.addAll(part2Loop(range))
            }
        }
        return invalidIDs
    }

    private fun part2Loop(range: String): Collection<BigInteger> {
        val invalidIDs: MutableList<BigInteger> = mutableListOf()
        val split = range.split("-")
        var currentIDbeingChecked = BigInteger(split[0])
        val upperLimitID = BigInteger(split[1])
        while (currentIDbeingChecked <= upperLimitID) {
            when {
                idRepeatsAtLeastTwice(currentIDbeingChecked) -> invalidIDs.add(currentIDbeingChecked)
            }
            currentIDbeingChecked = currentIDbeingChecked.add(BigInteger.ONE)
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
                idRepeatsTwice(currentIDbeingChecked) -> invalidIDs.add(currentIDbeingChecked)
            }
            currentIDbeingChecked = currentIDbeingChecked.add(BigInteger.ONE)
        }
        return invalidIDs
    }

    fun idRepeatsTwice(id: BigInteger): Boolean {
        val idAsString = id.toString()
        if (idAsString.length % 2 != 0) return false

        for (i in id.toString().length / 2..id.toString().length / 2) {
            if (id.toString().chunked(i).distinct().size == 1) return true;
        }
        return false
    }

    fun idRepeatsAtLeastTwice(id: BigInteger): Boolean {
        for (i in 1..id.toString().length / 2) {
            if (id.toString().chunked(i).distinct().size == 1) return true;
        }

        return false
    }

    fun sum(invalidIDs: List<BigInteger>): BigInteger {
        return invalidIDs.sumOf { it }
    }
}

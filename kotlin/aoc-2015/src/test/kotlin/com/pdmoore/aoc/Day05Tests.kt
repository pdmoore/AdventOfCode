package com.pdmoore.aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day05Tests : FunSpec( {

    /*
    A nice string is one with all of the following properties:
It does not contain the strings ab, cd, pq, or xy, even if they are part of one of the other requirements.
     */
    test("Can detect string with at least three vowels") {
        Day05.atLeastThreeVowels("abc") shouldBe false
        Day05.atLeastThreeVowels("aec") shouldBe false

        Day05.atLeastThreeVowels("aei") shouldBe true
        Day05.atLeastThreeVowels("xazegov") shouldBe true
        Day05.atLeastThreeVowels("aeiouaeiouaeiou") shouldBe true
    }

    test("Can detect one letter appears twice in a row") {
        Day05.atLeastOneLetterTwiceInARow("abc") shouldBe false
        Day05.atLeastOneLetterTwiceInARow("aba") shouldBe false

        Day05.atLeastOneLetterTwiceInARow("xx") shouldBe true
        Day05.atLeastOneLetterTwiceInARow("abcdde") shouldBe true
        Day05.atLeastOneLetterTwiceInARow("aabbccdd") shouldBe true
    }

    test("Can detect unwanted character pairs") {
        //ab, cd, pq, or xy
        Day05.containsUnwantedCharacterPair("abc") shouldBe true
        Day05.containsUnwantedCharacterPair("bcd") shouldBe true
        Day05.containsUnwantedCharacterPair("mnopqr") shouldBe true
        Day05.containsUnwantedCharacterPair("xyz") shouldBe true

        Day05.containsUnwantedCharacterPair("ace") shouldBe false
        Day05.containsUnwantedCharacterPair("cbd") shouldBe false
        Day05.containsUnwantedCharacterPair("poq") shouldBe false
    }

    test("Can detect nice strings") {
        Day05.isPart1Nice("ugknbfddgicrmopn") shouldBe true
        Day05.isPart1Nice("aaa") shouldBe true

        Day05.isPart1Nice("jchzalrnumimnmhp") shouldBe false
        Day05.isPart1Nice("haegwjzuvuyypxyu") shouldBe false
        Day05.isPart1Nice("dvszwmarrgswjxmb") shouldBe false
    }

    test("Can detect pair of any two letters that appears at least twice") {
        Day05.atLeastOnePairAppearingTwice("xyxy") shouldBe true
        Day05.atLeastOnePairAppearingTwice("aabcdefgaa") shouldBe true

        Day05.atLeastOnePairAppearingTwice("aaa") shouldBe false
    }

    test("Can detect letter repeat with exactly one letter between the two") {
        Day05.atLeastOneLetterRepeatWithOneLetterBetween("xyx") shouldBe true
        Day05.atLeastOneLetterRepeatWithOneLetterBetween("abcdefeghi") shouldBe true
        Day05.atLeastOneLetterRepeatWithOneLetterBetween("aaa") shouldBe true

        Day05.atLeastOneLetterRepeatWithOneLetterBetween("abc") shouldBe false
    }

    test("Solve part 1") {
        val input = PuzzleInput.asListOfStrings("./data/day05")
        val actual = Day05.solvePart1(input)
        actual shouldBe 255
    }

    test("Solve part 2") {
        val input = PuzzleInput.asListOfStrings("./data/day05")
        val actual = Day05.solvePart2(input)
        actual shouldBe 55
    }


})
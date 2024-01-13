package com.pdmoore.aoc

import io.kotest.core.spec.style.AnnotationSpec
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
        Day05.isNice("ugknbfddgicrmopn") shouldBe true
        Day05.isNice("aaa") shouldBe true

        Day05.isNice("jchzalrnumimnmhp") shouldBe false
        Day05.isNice("haegwjzuvuyypxyu") shouldBe false
        Day05.isNice("dvszwmarrgswjxmb") shouldBe false
    }


    test("Solve part 1") {
        // read in List<String>
        // count number that are nice
        val input = PuzzleInput.asListOfStrings("./data/day05")
        val actual = Day05.solvePart1(input)
        actual shouldBe 255
    }


})
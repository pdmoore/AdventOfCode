package com.pdmoore.aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day05Tests : FunSpec( {

    /*
    A nice string is one with all of the following properties:
It contains at least three vowels (aeiou only), like aei, xazegov, or aeiouaeiouaeiou.
It contains at least one letter that appears twice in a row, like xx, abcdde (dd), or aabbccdd (aa, bb, cc, or dd).
It does not contain the strings ab, cd, pq, or xy, even if they are part of one of the other requirements.
     */
    test("Can detect string with at least three vowels") {
        Day05.atLeastThreeVowels("abc") shouldBe false
        Day05.atLeastThreeVowels("aec") shouldBe false

        Day05.atLeastThreeVowels("aei") shouldBe true
        Day05.atLeastThreeVowels("xazegov") shouldBe true
        Day05.atLeastThreeVowels("aeiouaeiouaeiou") shouldBe true
    }


})
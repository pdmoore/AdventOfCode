package com.pdmoore.aoc

class Day05 {
    companion object {
        fun atLeastThreeVowels(s: String): Boolean {
            val filter = s.filter { it in listOf('a', 'e', 'i', 'o', 'u') }
            return filter.length >= 3
        }

        fun atLeastOneletterTwiceInARow(s: String): Boolean {
            for (i in 0..s.length - 2) {
                if (s[i] == s[i+1]) return true
            }
            return false
        }
    }

}

package com.pdmoore.aoc

class Day05 {


    companion object {
        val UNWANTED_PAIRS = listOf("ab", "cd", "pq", "xy")

        fun atLeastThreeVowels(s: String): Boolean {
            val filter = s.filter { it in listOf('a', 'e', 'i', 'o', 'u') }
            return filter.length >= 3
        }

        fun atLeastOneLetterTwiceInARow(s: String): Boolean {
            (0..s.length - 2).forEach { i ->
                if (s[i] == s[i+1]) return true
            }
            return false
        }

        fun containsUnwantedCharacterPair(s: String): Boolean {
            (0..s.length - 2).forEach { i ->
                if (Character.toString(s[i]).plus(s[i + 1]) in UNWANTED_PAIRS) return true
            }
            return false
        }

        fun isNice(s: String): Boolean {
            return atLeastThreeVowels(s) &&
                    atLeastOneLetterTwiceInARow(s) &&
                    !containsUnwantedCharacterPair(s)
        }

        fun solvePart1(input: List<String>): Int {
            return input.count { isNice(it) }
        }
    }

}

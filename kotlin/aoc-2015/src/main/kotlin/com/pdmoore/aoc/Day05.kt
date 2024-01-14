package com.pdmoore.aoc

class Day05 {


    companion object {
        val UNWANTED_PAIRS = listOf("ab", "cd", "pq", "xy")
        val VOWELS = listOf('a', 'e', 'i', 'o', 'u')

        fun atLeastThreeVowels(s: String): Boolean {
            val filter = s.filter { it in VOWELS }
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

        fun isPart1Nice(s: String): Boolean {
            return atLeastThreeVowels(s) &&
                    atLeastOneLetterTwiceInARow(s) &&
                    !containsUnwantedCharacterPair(s)
        }

        fun solvePart1(input: List<String>): Int {
            return input.count { isPart1Nice(it) }
        }

        fun atLeastOnePairAppearingTwice(s: String): Boolean {
            (0..s.length - 2).forEach { i ->
                val thisPair = s.substring(i, i+2)
                if (s.substring(i + 2).contains(thisPair)) return true
            }
            return false
        }
    }

}

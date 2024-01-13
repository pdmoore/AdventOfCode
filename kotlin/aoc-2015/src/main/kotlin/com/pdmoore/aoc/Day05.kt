package com.pdmoore.aoc

class Day05 {
    companion object {
        fun atLeastThreeVowels(s: String): Boolean {
            val filter = s.filter { it in listOf('a', 'e', 'i', 'o', 'u') }
            return filter.length >= 3
        }

        fun atLeastOneLetterTwiceInARow(s: String): Boolean {
//            (0..s.length - 2).forEach { i -> if (s[i] == s[i + 1]) return true }
            (0..s.length - 2).forEach { i ->
                if (s[i] == s[i+1]) return true
            }
            return false
        }

        fun containsUnwantedCharacterPair(s: String): Boolean {
            val unwantedPairs = listOf("ab", "cd", "pq", "xy")
            for (i in 0..s.length - 2) {
                val thisPair = "" + s[i] + s[i+1]
                if (thisPair in unwantedPairs) return true
            }
            return false
        }
    }

}

package com.pdmoore.aoc

class Day05 {
    companion object {
        fun atLeastThreeVowels(s: String): Boolean {
            val filter = s.filter { it in listOf('a', 'e', 'i', 'o', 'u') }
            return filter.length >= 3
        }
    }

}

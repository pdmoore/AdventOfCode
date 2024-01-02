package com.pdmoore.aoc

class Day01 {
    companion object {
        fun solvePart1(inputLine: String): Int {
            var floor = 0
            inputLine.forEach { c ->
                floor += upOrDown(c)
            }
            return floor
        }

        fun solvePart2(inputLine: String): Int {
            var floor = 0
            for (i in inputLine.indices) {
                floor += upOrDown(inputLine[i])
                if (floor < 0) return i + 1
            }
            throw IllegalArgumentException("Never entered basement for $inputLine")
        }
        private fun upOrDown(c: Char): Int {
            if ('(' == c) return 1
            return -1
        }
    }
}

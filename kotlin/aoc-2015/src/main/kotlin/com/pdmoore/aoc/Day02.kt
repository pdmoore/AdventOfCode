package com.pdmoore.aoc

import kotlin.math.max
import kotlin.math.min

class Day02 {
    companion object {
        fun solvePart1(inputLine: String): Int {
            val (l, w, h) = extractLengthWidthHeight(inputLine)
            return surfaceAreaOf(l, w, h) + areaOfSmallestSide(l, w, h)
        }

        fun solvePart2(inputLine: String): Int {
            return smallestPerimeterOf(inputLine) + volumeOf(inputLine)
        }

        fun solvePart1(inputLine: List<String>): Int {
            return inputLine.sumOf { i -> solvePart1(i) }
        }

        fun solvePart2(inputLine: List<String>): Int {
            return inputLine.sumOf { i -> solvePart2(i) }
        }

        fun areaOfSmallestSide(inputLine: String): Int {
            val (l, w, h) = extractLengthWidthHeight(inputLine)
            return areaOfSmallestSide(l, w, h)
        }

        fun surfaceAreaOf(inputLine: String): Int {
            val (l, w, h) = extractLengthWidthHeight(inputLine)
            return surfaceAreaOf(l, w, h)
        }

        private fun extractLengthWidthHeight(inputLine: String): Triple<Int, Int, Int> {
            val split = inputLine.split("x")
            val l = split[0].toInt()
            val w = split[1].toInt()
            val h = split[2].toInt()
            return Triple(l, w, h)
        }

        private fun areaOfSmallestSide(l: Int, w: Int, h: Int): Int {
            return min(l.times(w), min(w.times(h), l.times(h)))
        }

        private fun surfaceAreaOf(l: Int, w: Int, h: Int): Int {
            return (l.times(w).times(2)).plus(
                   (w.times(h).times(2))).plus(
                   (l.times(h).times(2)))
        }

        fun volumeOf(inputLine: String): Int {
            val (l, w, h) = extractLengthWidthHeight(inputLine)
            return l.times(w).times(h)
        }

        fun smallestPerimeterOf(inputLine: String): Int {
            val (l, w, h) = extractLengthWidthHeight(inputLine)
            val smallest = min(min(l, w), h)
            val secondSmallest = max(min(l, w), min(max(l, w), h))

            return smallest.times(2).plus(secondSmallest.times(2))
        }
    }
}

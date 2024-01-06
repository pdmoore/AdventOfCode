package com.pdmoore.aoc

class Day03 {
    companion object {
        fun houseCountSantaDeliversTo(input: String): Int {
            var current = Point2D(0, 0)

            val housesVisited = mutableSetOf(current)
            for (c in input) {
                current = moveTo(c, current)
                housesVisited.add(current)
            }

            return housesVisited.size
        }

        fun houseCountWithRoboSantaDeliveries(input: String): Int {
            var currentSanta = Point2D(0, 0)
            var currentRoboSanta = Point2D(0, 0)

            var santaMovesOnEven = 0

            val housesVisited = mutableSetOf(currentSanta, currentRoboSanta)
            for (c in input) {
                if (santaMovesOnEven++ % 2 == 0) {
                    currentSanta = moveTo(c, currentSanta)
                    housesVisited.add(currentSanta)
                } else {
                    currentRoboSanta = moveTo(c, currentRoboSanta)
                    housesVisited.add(currentRoboSanta)
                }
            }

            return housesVisited.size
        }

        private fun moveTo(c: Char, currentSanta: Point2D) = when (c) {
            '>' -> currentSanta.right()
            '<' -> currentSanta.left()
            '^' -> currentSanta.up()
            'v' -> currentSanta.down()
            else -> {
                throw IllegalArgumentException("Cannot moveTo unknown character $c")
            }
        }
    }
}

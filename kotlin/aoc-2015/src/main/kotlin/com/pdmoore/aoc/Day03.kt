package com.pdmoore.aoc

class Day03 {
    companion object {
        const val DO_NOT_USE_ROBO_SANTA = false
        const val USE_ROBO_SANTA = true

        fun houseCountSantaDeliversTo(input: String): Int {
            return countHousesVisited(input, DO_NOT_USE_ROBO_SANTA)
        }

        fun houseCountWithRoboSantaDeliveries(input: String): Int {
            return countHousesVisited(input, USE_ROBO_SANTA)
        }

        private fun countHousesVisited(input: String, useRoboSanta: Boolean): Int {
            var santaPosition = Point2D(0, 0)
            var roboSantaPosition = Point2D(0, 0)

            var santaMovesOnEven = 0

            val housesVisited = mutableSetOf(santaPosition, roboSantaPosition)
            for (c in input) {
                if (!useRoboSanta || santaMovesOnEven++ % 2 == 0) {
                    santaPosition = moveTo(c, santaPosition)
                    housesVisited.add(santaPosition)
                } else {
                    roboSantaPosition = moveTo(c, roboSantaPosition)
                    housesVisited.add(roboSantaPosition)
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

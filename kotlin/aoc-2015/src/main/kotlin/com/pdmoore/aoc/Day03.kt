package com.pdmoore.aoc

class Day03 {
    companion object {
        fun houseCountSantaDeliversTo(input: String): Int {
            var current = Point2D(0, 0)

            val housesVisited = mutableSetOf(current)
            for (c in input) {
                current = when (c) {
                    '>' -> current.right()
                    '<' -> current.left()
                    '^' -> current.up()
                    'v' -> current.down()
                    else -> {
                        throw IllegalArgumentException("unknown character $c")
                    }
                }
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

                    currentSanta = when (c) {
                        '>' -> currentSanta.right()
                        '<' -> currentSanta.left()
                        '^' -> currentSanta.up()
                        'v' -> currentSanta.down()
                        else -> {
                            throw IllegalArgumentException("unknown character $c")
                        }
                    }
                    housesVisited.add(currentSanta)
                } else {

                    currentRoboSanta = when (c) {
                        '>' -> currentRoboSanta.right()
                        '<' -> currentRoboSanta.left()
                        '^' -> currentRoboSanta.up()
                        'v' -> currentRoboSanta.down()
                        else -> {
                            throw IllegalArgumentException("unknown character $c")
                        }
                    }
                    housesVisited.add(currentRoboSanta)
                }


            }

            return housesVisited.size

        }
    }
}

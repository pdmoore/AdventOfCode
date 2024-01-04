package com.pdmoore.aoc

class Day03 {
    companion object {
        fun housesThatReceiveAtLeastOnePresent(input: String): Int {
            var current = Point2D(0, 0)

            val housesVisited = mutableSetOf(current)
            for (c in input) {
                when (c) {
                    '>' -> current = current.right()
                    '<' -> current = current.left()
                    '^' -> current = current.up()
                    'v' -> current = current.down()
                    else -> { throw IllegalArgumentException("unknown character $c")}
                }
                housesVisited.add(current)
            }

            return housesVisited.size
        }
    }
}

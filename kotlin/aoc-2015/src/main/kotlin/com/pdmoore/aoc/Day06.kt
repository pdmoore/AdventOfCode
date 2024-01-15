package com.pdmoore.aoc

class Day06(maxX: Int, maxY: Int) {

        val litLights = HashSet<Pair<Int, Int>>()

    fun followInstruction(input: String) {
        // "turn on 0,0 through 2,2"
        val rhs = input.substring(7)
        val split = rhs.split(" through ")
        val startCoords = split[0].split(",")
        val xFrom = startCoords[0].trim().toInt()
        val yFrom = startCoords[1].trim().toInt()
        val endCoords   = split[1].split(",")
        val xTo = endCoords[0].trim().toInt()
        val yTo   = endCoords[1].trim().toInt()
        for (x in xFrom..xTo) {
            for (y in yFrom..yTo) {
                litLights.add(Pair(x, y))
            }
        }
    }

    fun countOfLitLights(): Int {
        return litLights.size
    }


}

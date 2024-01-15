package com.pdmoore.aoc

class Day06(maxX: Int, maxY: Int) {

    val litLights = HashSet<Pair<Int, Int>>()

    class Rectangle(val upperLeft: Pair<Int, Int>, val lowerRight: Pair<Int, Int>) {
    }


    fun followInstruction(input: String) {

        if (input.startsWith("turn on")) {
            val cornersString = input.substring(input.indexOf("on") + "on".length + 1)
            val rectangle = parseRectangle(cornersString)

            for (x in rectangle.upperLeft.first..rectangle.lowerRight.first) {
                for (y in rectangle.upperLeft.second..rectangle.lowerRight.second) {
                    litLights.add(Pair(x, y))
                }
            }
        } else if (input.startsWith("turn off")) {
            val cornersString = input.substring(input.indexOf("off") + "off".length + 1)
            val rectangle = parseRectangle(cornersString)
            for (x in rectangle.upperLeft.first..rectangle.lowerRight.first) {
                for (y in rectangle.upperLeft.second..rectangle.lowerRight.second) {
                    litLights.remove(Pair(x, y))
                }
            }
        } else {
            // TOGGLE
            val cornersString = input.substring(input.indexOf("toggle") + "toggle".length + 1)
            val rectangle = parseRectangle(cornersString)
            for (x in rectangle.upperLeft.first..rectangle.lowerRight.first) {
                for (y in rectangle.upperLeft.second..rectangle.lowerRight.second) {
                    val thisLight = Pair(x, y)
                    if (litLights.contains(thisLight)) {
                        litLights.remove(thisLight)
                    } else {
                        litLights.add(Pair(x, y))
                    }
                }
            }
        }
    }

    private fun parseRectangle(rhs: String): Rectangle {
        val split = rhs.split(" through ")
        val startCoords = split[0].split(",")
        val xFrom = startCoords[0].trim().toInt()
        val yFrom = startCoords[1].trim().toInt()
        val endCoords = split[1].split(",")
        val xTo = endCoords[0].trim().toInt()
        val yTo = endCoords[1].trim().toInt()
        val rectangle = Rectangle(Pair(xFrom, yFrom), Pair(xTo, yTo))
        return rectangle
    }

    fun countOfLitLights(): Int {
        return litLights.size
    }

    fun followInstructions(input: List<String>) {
        for (inputLine in input) {
            followInstruction(inputLine)
        }
    }


}

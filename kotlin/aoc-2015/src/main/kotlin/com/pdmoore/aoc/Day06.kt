package com.pdmoore.aoc

import kotlin.math.max

private const val ARRAY_SIZE = 1000

class Day06 {

    val litLights = HashSet<Pair<Int, Int>>()
    val litLights2D: Array<Array<Int>> = Array(ARRAY_SIZE) { Array(ARRAY_SIZE) { 0 } }

    class Rectangle(val upperLeft: Pair<Int, Int>, val lowerRight: Pair<Int, Int>)

    abstract class AbstractProcessor() {
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

        fun processEachInstruction(input: String) {
            if (input.startsWith("turn on")) {
                val cornersString = input.substring(input.indexOf("on") + "on".length + 1)
                val rectangle = parseRectangle(cornersString)
                for (x in rectangle.upperLeft.first..rectangle.lowerRight.first) {
                    for (y in rectangle.upperLeft.second..rectangle.lowerRight.second) {
                        turnOnAction(x, y)
                    }
                }
            } else if (input.startsWith("turn off")) {
                val cornersString = input.substring(input.indexOf("off") + "off".length + 1)
                val rectangle = parseRectangle(cornersString)
                for (x in rectangle.upperLeft.first..rectangle.lowerRight.first) {
                    for (y in rectangle.upperLeft.second..rectangle.lowerRight.second) {
                        turnOffAction(x, y)
                    }
                }
            } else {
                // TOGGLE
                val cornersString = input.substring(input.indexOf("toggle") + "toggle".length + 1)
                val rectangle = parseRectangle(cornersString)
                for (x in rectangle.upperLeft.first..rectangle.lowerRight.first) {
                    for (y in rectangle.upperLeft.second..rectangle.lowerRight.second) {
                        toggleAction(x, y)
                    }
                }
            }
        }

        abstract fun turnOnAction(x: Int, y: Int)
        abstract fun turnOffAction(x: Int, y: Int)
        abstract fun toggleAction(x: Int, y: Int)
    }

    class ProcessVia2DArray(val input: List<String>) : AbstractProcessor() {
        constructor() : this(emptyList())

        val litLights2D: Array<Array<Int>> = Array(ARRAY_SIZE) { Array(ARRAY_SIZE) { 0 } }

        override fun turnOnAction(x: Int, y: Int) {
            litLights2D[x][y] = 1
        }

        override fun turnOffAction(x: Int, y: Int) {
            litLights2D[x][y] = 0
        }

        override fun toggleAction(x: Int, y: Int) {
            litLights2D[x][y] = 1 - litLights2D[x][y]
        }

        fun countOfLitLightsUsingArray(): Int {
            var result = 0
            (0..<ARRAY_SIZE).forEach { x ->
                (0..<ARRAY_SIZE)
                    .asSequence()
                    .filter { litLights2D[x][it] > 0 }
                    .forEach { result++ }
            }
            return result
        }

        fun processInstructions() {
            for (inputLine in input) {
                processEachInstruction(inputLine)
            }
        }
    }


//    fun followInstructionUsing2DArray(input: String) {
//        // TODO push param to Abstract ctor
//        val processor = ProcessVia2DArray(input)
//    }

    // TODO - use a CTOR flag when constructing which changes toggle behavior
    // then combine this and the above
    fun followInstructionsWithBrightness(input: String) {
        if (input.startsWith("turn on")) {
            val cornersString = input.substring(input.indexOf("on") + "on".length + 1)
            val rectangle = parseRectangle(cornersString)

            for (x in rectangle.upperLeft.first..rectangle.lowerRight.first) {
                for (y in rectangle.upperLeft.second..rectangle.lowerRight.second) {
                    litLights2D[x][y] += 1
                }
            }
        } else if (input.startsWith("turn off")) {
            val cornersString = input.substring(input.indexOf("off") + "off".length + 1)
            val rectangle = parseRectangle(cornersString)
            for (x in rectangle.upperLeft.first..rectangle.lowerRight.first) {
                for (y in rectangle.upperLeft.second..rectangle.lowerRight.second) {
                    litLights2D[x][y] = max(0, litLights2D[x][y] - 1)
                }
            }
        }else {
            // TOGGLE
            val cornersString = input.substring(input.indexOf("toggle") + "toggle".length + 1)
            val rectangle = parseRectangle(cornersString)
            for (x in rectangle.upperLeft.first..rectangle.lowerRight.first) {
                for (y in rectangle.upperLeft.second..rectangle.lowerRight.second) {
                    litLights2D[x][y] += 2
                }
            }
        }

    }

    fun followInstructionUsingSet(input: String) {

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

    fun countOfLitLightsUsingArray(): Int {
        var result = 0
        (0..<ARRAY_SIZE).forEach { x ->
            (0..<ARRAY_SIZE)
                .asSequence()
                .filter { litLights2D[x][it] > 0 }
                .forEach { result++ }
        }
        return result
    }


    fun countOfBrightness(): Int {
        var result = 0
        (0..<ARRAY_SIZE).forEach { x ->
            (0..<ARRAY_SIZE)
                .asSequence()
                .forEach { result += litLights2D[x][it] }
        }
        return result
    }

    fun countOfLitLightsUsingSet(): Int {
        return litLights.size
    }

    fun followInstructionsUsingSet(input: List<String>) {
        for (inputLine in input) {
            followInstructionUsingSet(inputLine)
        }
    }

//    fun followInstructionsUsingArray(input: List<String>) {
//        for (inputLine in input) {
//            followInstructionUsing2DArray(inputLine)
//        }
//    }

    fun part2(input: List<String>) {
        for (inputLine in input) {
            followInstructionsWithBrightness(inputLine)
        }
    }
}

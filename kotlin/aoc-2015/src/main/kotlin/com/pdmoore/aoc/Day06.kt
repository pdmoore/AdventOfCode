package com.pdmoore.aoc

import kotlin.math.max

private const val ARRAY_SIZE = 1000

class Day06 {

    abstract class AbstractProcessor(private val input: List<String>) {
        fun processAllInstructions() {
            for (inputLine in input) {
                processSingleInstruction(inputLine)
            }
        }

        enum class Command { ON, OFF, TOGGLE }

        fun processSingleInstruction(input: String) {
            var cornersString = ""
            var command = Command.OFF
            when {
                input.contains("on") -> {
                    command = Command.ON
                    cornersString = input.substring(input.indexOf("on") + "on".length + 1)
                }
                input.contains("off") -> {
                    command = Command.OFF
                    cornersString = input.substring(input.indexOf("off") + "off".length + 1)
                }
                input.contains("toggle") -> {
                    command = Command.TOGGLE
                    cornersString = input.substring(input.indexOf("toggle") + "toggle".length + 1)
                }
            }

            val rectangle = parseRectangle(cornersString)
            for (x in rectangle.upperLeft.first..rectangle.lowerRight.first) {
                for (y in rectangle.upperLeft.second..rectangle.lowerRight.second) {
                    when (command) {
                        Command.ON -> turnOnAction(x, y)
                        Command.OFF -> turnOffAction(x, y)
                        Command.TOGGLE -> toggleAction(x, y)
                    }
                }
            }
        }

        abstract fun turnOnAction(x: Int, y: Int)
        abstract fun turnOffAction(x: Int, y: Int)
        abstract fun toggleAction(x: Int, y: Int)

        class Rectangle(val upperLeft: Pair<Int, Int>, val lowerRight: Pair<Int, Int>)

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
    }

    class ProcessVia2DArray(input: List<String>) : AbstractProcessor(input) {
        constructor() : this(emptyList())

        private val litLights2D: Array<Array<Int>> = Array(ARRAY_SIZE) { Array(ARRAY_SIZE) { 0 } }

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
                repeat((0..<ARRAY_SIZE)
                    .asSequence()
                    .filter { litLights2D[x][it] > 0 }.count()
                ) { result++ }
            }
            return result
        }
    }

    class ProcessViaBrightness(input: List<String>) : AbstractProcessor(input) {
        constructor() : this(emptyList())

        private val litLights2D: Array<Array<Int>> = Array(ARRAY_SIZE) { Array(ARRAY_SIZE) { 0 } }

        override fun turnOnAction(x: Int, y: Int) {
            litLights2D[x][y] += 1
        }

        override fun turnOffAction(x: Int, y: Int) {
            litLights2D[x][y] = max(0, litLights2D[x][y] - 1)
        }

        override fun toggleAction(x: Int, y: Int) {
            litLights2D[x][y] += 2
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
    }

    class ProcessViaSet(input: List<String>) : AbstractProcessor(input) {
        constructor() : this(emptyList())

        private val litLights = HashSet<Pair<Int, Int>>()

        override fun turnOnAction(x: Int, y: Int) {
            litLights.add(Pair(x, y))
        }

        override fun turnOffAction(x: Int, y: Int) {
            litLights.remove(Pair(x, y))
        }

        override fun toggleAction(x: Int, y: Int) {
            val thisLight = Pair(x, y)
            if (litLights.contains(thisLight)) {
                litLights.remove(thisLight)
            } else {
                litLights.add(Pair(x, y))
            }
        }

        fun countOfLitLightsUsingSet(): Int {
            return litLights.size
        }
    }
}

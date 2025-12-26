package com.pdmoore.aoc

open class Dial(position: Int, pointingAtZeroCount: Int = 0) {
    var pointingAtZeroCount: Int = pointingAtZeroCount
    val position = position

    fun rotate(rotation: String): Dial {
        val leftOrRight = if (rotation.contains("L")) -1 else 1;
        val distance = rotation.substring(1).toInt()


        var newPosition = (position + (leftOrRight * distance))
        if (leftOrRight == 1) {
            while (newPosition >= 100) {
                pointingAtZeroCount += 1
                newPosition -= 100
            }
        } else {
            if (newPosition == 0) {
                 pointingAtZeroCount += 1
            } else if (distance > 100) {
                pointingAtZeroCount += (distance / 100) + 1
                while (newPosition < 0) {
                    newPosition += 100
                }
            } else {
                while (newPosition < 0) {
                    newPosition += 100
                    pointingAtZeroCount += 1
                }
            }

            if (position == 0) {
                pointingAtZeroCount -= 1
            }
        }
        return Dial(newPosition, pointingAtZeroCount)
    }

    companion object {
        fun rotate(input: List<String>): Dial {
            var currentDial = Dial(50)
            input.forEach { i ->
                currentDial = currentDial.rotate(i)
            }
            return currentDial
        }
    }
}
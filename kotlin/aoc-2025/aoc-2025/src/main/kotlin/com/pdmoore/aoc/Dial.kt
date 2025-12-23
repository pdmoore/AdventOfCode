package com.pdmoore.aoc

open class Dial(position: Int) {
    val position = position
    fun rotate(rotation: String): Dial {
        val distance = rotation.substring(1).toInt() % 100

        val leftOrRight = if (rotation.contains("L")) -1 else 1;
        var newPosition = position + (leftOrRight * distance)

        if (newPosition >= 100) newPosition -= 100
        if (newPosition < 0) newPosition += 100
        return Dial(newPosition)
    }

    companion object {
        fun rotate(input: List<String>): Dial {
            var currentDial = Dial(50)
            for (i in input) {
                currentDial = currentDial.rotate(i)
            }
            return currentDial
        }
    }

}
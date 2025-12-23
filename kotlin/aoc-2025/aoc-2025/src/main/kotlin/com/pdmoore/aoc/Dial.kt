package com.pdmoore.aoc

open class Dial(position: Int) {
    val position = position
    fun rotate(rotation: String): Dial {
        val leftOrRight = if (rotation.contains("L")) -1 else 1;
        val distance = rotation.substring(1).toInt() % 100
        var newPosition = position + (leftOrRight * distance)

        if (newPosition >= 100) newPosition = newPosition % 100
        if (newPosition < 0) newPosition += 100
        return Dial(newPosition)
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
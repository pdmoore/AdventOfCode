package com.pdmoore.aoc

open class Dial(position: Int) {
    val position = position
    fun rotate(rotation: String): Dial {
        if (rotation.contains("R")) {
            var newPosition = position + rotation.substring(1).toInt()
            if (newPosition >= 100) newPosition -= 100
            return Dial(newPosition)
        }
        else {
            var newPosition = position - rotation.substring(1).toInt()
            if (newPosition < 0) newPosition += 100
            return Dial(newPosition)
        }
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
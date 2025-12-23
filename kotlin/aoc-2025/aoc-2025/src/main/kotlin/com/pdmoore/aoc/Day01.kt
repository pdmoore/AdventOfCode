package com.pdmoore.aoc

open class Day01(val input: List<String>) {
    fun solve() : Int {
        var zeroCount = 0
        var currentDial = Dial(50)
        input.forEach { rotation ->
            currentDial = currentDial.rotate(rotation)
            if (currentDial.position == 0) zeroCount++
        }

        return zeroCount
    }
}

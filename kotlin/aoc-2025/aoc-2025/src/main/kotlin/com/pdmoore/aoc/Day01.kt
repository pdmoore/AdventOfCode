package com.pdmoore.aoc

open class Day01(val input: List<String>) {
    fun solve(): Int {
        var zeroCount = 0
        var currentDial = Dial(50)
        input.forEach { rotation ->
            currentDial = currentDial.rotate(rotation)
            if (currentDial.position == 0) zeroCount++
        }

        return zeroCount
    }

// This solution tries to do the math and track as dial passes over/on zero
// it comes up with too many clicks for Part 2
// need to ID the case(s) where one too many clicks are recorded
    fun solvePart2() : Int {
        var currentDial = Dial(50)
        input.forEach { rotation ->
            currentDial = currentDial.rotate(rotation)
        }

        return currentDial.pointingAtZeroCount
    }

// This implementation does a single click in the correct directtion for the input distance
// it comes up with the correct answer for Part 2
//    fun solvePart2(): Int {
//        var zeroCount = 0
//        var currentDial = Dial(50)
//        input.forEach { rotation: String ->
//            val leftOrRight = if (rotation.contains("L")) -1 else 1;
//            val clicks = rotation.substring(1).toInt()
//
//            val ch: Char = rotation[0]
//            for (i in 1..clicks) {
//                val singleClick = "$ch"+"1"
//                currentDial = currentDial.rotate(singleClick)
//                if (currentDial.position == 0) zeroCount++
//            }
//        }
//
//        return zeroCount
//    }
}

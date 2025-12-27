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

//    fun solvePart2() : Int {
//        var currentDial = Dial(50)
//        input.forEach { rotation ->
//            currentDial = currentDial.rotate(rotation)
//        }
//
//        return currentDial.pointingAtZeroCount
//    }

    fun solvePart2(): Int {
        var zeroCount = 0
        var currentDial = Dial(50)
        input.forEach { rotation: String ->
            val leftOrRight = if (rotation.contains("L")) -1 else 1;
            val clicks = rotation.substring(1).toInt()

            val ch: Char = rotation[0]
            for (i in 1..clicks) {
                val singleClick = "$ch"+"1"
                currentDial = currentDial.rotate(singleClick)
                if (currentDial.position == 0) zeroCount++
            }
        }

        return zeroCount
    }
}

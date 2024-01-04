package com.pdmoore.aoc

class Point2D(val _x: Int, val _y: Int) {
    val x = _x
    val y = _y

    companion object {
        fun right(): Point2D {
            return Point2D(1, 0)
        }
    }

}

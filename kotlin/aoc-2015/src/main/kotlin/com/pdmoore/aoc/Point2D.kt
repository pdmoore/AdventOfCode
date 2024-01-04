package com.pdmoore.aoc

class Point2D(val _x: Int, val _y: Int) {
    val x = _x
    val y = _y

    fun right(): Point2D = Point2D(x + 1, y)

}

package com.pdmoore.aoc

class Point2D(val _x: Int, val _y: Int) {
    val x = _x
    val y = _y

    fun right(): Point2D = Point2D(x + 1, y)
    fun left(): Point2D = Point2D(x - 1, y)
    fun up(): Point2D = Point2D(x, y - 1)
    fun down(): Point2D = Point2D(x, y + 1)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Point2D) return false

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }
}

package com.pdmoore.aoc

class Point2D(val x: Int, val y: Int) {
    fun right(): Point2D = Point2D(this.x + 1, this.y)
    fun left(): Point2D = Point2D(this.x - 1, this.y)
    fun up(): Point2D = Point2D(this.x, this.y - 1)
    fun down(): Point2D = Point2D(this.x, this.y + 1)

    // TODO - add unit tests for equals and hashcode
    // currently only confirmed via Day03 part 1 passing
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Point2D) return false

        if (this.x != other.x) return false
        if (this.y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + this.x
        result = prime * result + this.y
        return result
    }
}

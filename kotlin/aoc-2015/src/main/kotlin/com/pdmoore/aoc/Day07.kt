package com.pdmoore.aoc

class Day07 {

    private var identifierToSignal = mutableMapOf<String, Int>()

    fun processInputLine(input: String) {
        val split = input.split(" -> ")

        if (split[0].contains("AND")) {
            val splitAnd = split[0].split(" AND ")
            val lhs = identifierToSignal[splitAnd[0]]
            val rhs = identifierToSignal[splitAnd[1]]

            identifierToSignal[split[1]] = lhs?.and(rhs!!) ?: -99
        } else {
            identifierToSignal[split[1]] = split[0].toInt()
        }

    }

    fun valueOf(identifier: String): Int {
        return identifierToSignal[identifier]!!
    }

    fun processInput(input: List<String>) {
        input.forEach { inputLine -> processInputLine(inputLine) }
    }

}

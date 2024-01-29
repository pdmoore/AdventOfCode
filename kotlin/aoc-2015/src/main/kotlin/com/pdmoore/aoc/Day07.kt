package com.pdmoore.aoc

class Day07 {

    private var IDtoSignal: MutableMap<String, Int>

    init {
        IDtoSignal = mutableMapOf<String, Int>()
    }

    fun processInputLine(input: String) {
        val split = input.split(" -> ")

        if (split[0].contains("AND")) {
            val splitAnd = split[0].split(" AND ")
            val lhs = IDtoSignal.get(splitAnd[0])
            val rhs = IDtoSignal.get(splitAnd[1])

            IDtoSignal.put(split[1], lhs?.and(rhs!!) ?: -99)
        } else {
            IDtoSignal.put(split[1], split[0].toInt())
        }

    }

    fun valueOf(identifier: String): Int {
        return IDtoSignal.get(identifier)!!
    }

    fun processInput(input: List<String>) {
        input.forEach { inputLine -> processInputLine(inputLine) }
    }

}

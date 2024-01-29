package com.pdmoore.aoc

class Day07 {

    private var identifierToSignal = mutableMapOf<String, Int>()

    fun processInputLine(input: String) {
        val split = input.split(" -> ")

        when {
            split[0].contains("AND") -> {
                val splitAnd = split[0].split(" AND ")
                val lhs = identifierToSignal[splitAnd[0]]
                val rhs = identifierToSignal[splitAnd[1]]

                identifierToSignal[split[1]] = lhs?.and(rhs!!) ?: -99
            }
            split[0].contains("OR") -> {
                val splitOr = split[0].split(" OR ")
                val lhs = identifierToSignal[splitOr[0]]
                val rhs = identifierToSignal[splitOr[1]]

                identifierToSignal[split[1]] = lhs?.or(rhs!!) ?: -88
            }
            split.contains("NOT") -> {
                val splitNot = split[0].split("NOT ")
                val rhs = identifierToSignal[splitNot[0]]

                identifierToSignal[split[1]] = rhs!!.inv()
            }
            split[0].contains("LSHIFT") -> {
                val splitLshift = split[0].split(" LSHIFT ")
                val lhs = identifierToSignal[splitLshift[0]]
                val rhs = splitLshift[1].toInt()

                if (lhs != null) {
                    identifierToSignal[split[1]] = lhs shl rhs
                }
            }
            split[0].contains("RSHIFT") -> {
                val splitRshift = split[0].split(" RSHIFT ")
                val lhs = identifierToSignal[splitRshift[0]]
                val rhs = splitRshift[1].toInt()

                if (lhs != null) {
                    identifierToSignal[split[1]] = lhs shr rhs
                }
            }
            else -> {
                identifierToSignal[split[1]] = split[0].toInt()
            }
        }

    }

    fun valueOf(identifier: String): Int {
        return identifierToSignal.getValue(identifier)
    }

    fun processInput(input: List<String>) {
        input.forEach { inputLine -> processInputLine(inputLine) }
    }

}

package com.pdmoore.aoc

class Day07 {

    private lateinit var IDtoSignal: MutableMap<String, Int>

    fun processInputLine(input: String) {

        IDtoSignal = mutableMapOf<String, Int>()
        val split = input.split(" -> ")
        IDtoSignal.put(split[1], split[0].toInt())
    }

    fun valueOf(identifier: String): Int {
        return IDtoSignal.get(identifier)!!
    }

}

package com.pdmoore.aoc

import java.io.File

class PuzzleInput {
    companion object {
        fun asListOfStrings(filename: String): List<String> = File(filename).readLines()

        fun asSingleLine(filename: String): String  =  File(filename).readLines()[0]
    }
}
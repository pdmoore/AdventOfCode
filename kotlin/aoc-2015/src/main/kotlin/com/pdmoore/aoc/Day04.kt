package com.pdmoore.aoc

import java.math.BigInteger
import java.security.MessageDigest
import kotlin.text.Charsets.UTF_8

class Day04 {
    companion object {
        fun generateMD5(input: String): String {
            val md = MessageDigest.getInstance("MD5")
            val toByteArray = input.toByteArray(UTF_8)
            val hash = md.digest(toByteArray)
            val bi = BigInteger(1, hash)
            return bi.toString(16).padStart(32, '0')
        }

        fun startsWithZeros(input: String, zeroCount: Int): Boolean {
            val targetPrefix = "0".repeat(zeroCount)
            return input.startsWith(targetPrefix)
        }

        fun solvePart1(secret: String): Int {
            var i = 0
            while (i < Int.MAX_VALUE) {
                val hash = generateMD5(secret + i)
                if (startsWithZeros(hash, 5)) return i
                i++;
            }

            throw IllegalArgumentException("no answer found for $secret")
        }

        fun solvePart2(secret: String): Any {
            var i = 0
            while (i < Int.MAX_VALUE) {
                val hash = generateMD5(secret + i)
                if (startsWithZeros(hash, 6)) return i
                i++;
            }

            throw IllegalArgumentException("no answer found for $secret")
        }

    }

}

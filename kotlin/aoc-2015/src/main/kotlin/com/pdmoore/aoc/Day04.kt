package com.pdmoore.aoc

import java.math.BigInteger
import java.security.MessageDigest
import kotlin.text.Charsets.UTF_8

class Day04 {
    companion object {
        val MD5_DIGEST = MessageDigest.getInstance("MD5")
        val ZERO_BYTE = 0.toByte()

        fun generateMD5(input: String): String {
            val toByteArray = input.toByteArray(UTF_8)
            val hash = MD5_DIGEST.digest(toByteArray)
            val bi = BigInteger(1, hash)
            return bi.toString(16).padStart(32, '0')
        }

        fun startsWithZeros(input: String, zeroCount: Int): Boolean {
            val targetPrefix = "0".repeat(zeroCount)
            return input.startsWith(targetPrefix)
        }

        fun solvePart1(secret: String): Int {
            return solve(secret, 5)
        }

        fun solvePart2(secret: String): Any {
            return solveButCheckBytesBeforeStrings(secret)
        }

        private fun solve(secret: String, zerosInPrefix: Int): Int {
            repeat(Int.MAX_VALUE) {i ->
                val hash = generateMD5(secret + i)
                if (startsWithZeros(hash, zerosInPrefix)) return i
            }

// Started to look at multiprocessor attack of the problem
// following code finds the answer, but returns the hash, not the number that
// generated it.
// Could have generateMD5 return a pair (it, hash) and then
// have startsWith return it
// Brief reading led me to believe there's not much improvement opportunity
//            val found = (1..Int.MAX_VALUE).asSequence()
//                .map { generateMD5(secret + it) }
//                .find { startsWithZeros(it, zerosInPrefix) }

            throw IllegalArgumentException("no answer found for $secret")
        }

        private fun solveButCheckBytesBeforeStrings(secret: String): Int {
            repeat(Int.MAX_VALUE) {i ->
                val input = secret + i
                val toByteArray = input.toByteArray(UTF_8)
                val hash = MD5_DIGEST.digest(toByteArray)
                // 6 zeros are represented in 3 half-bytes
                if (hash[0] == ZERO_BYTE &&
                    hash[1] == ZERO_BYTE &&
                    hash[2] == ZERO_BYTE) {
                    return i
                }
            }

            throw IllegalArgumentException("no answer found for $secret")
        }
    }
}

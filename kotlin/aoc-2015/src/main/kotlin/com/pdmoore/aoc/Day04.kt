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

        fun startsWithZeros(input: String): Boolean {
            return input.startsWith("00000")
        }
    }

}

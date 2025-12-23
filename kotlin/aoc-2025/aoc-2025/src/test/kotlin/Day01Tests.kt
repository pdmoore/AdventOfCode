package com.pdmoore.aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import javax.swing.text.Position

class Day01Tests : FunSpec ({

    // handle multiple turns
    // count number of times it stops on 0

    test("Right hand rotation, no wrap around") {
        Dial(50).rotate("R11").position shouldBe 61
    }

    test("Left rotation, no wrap around") {
        Dial(50).rotate("L11").position shouldBe 39
    }

    test("Left rotation, wrap around") {
        Dial(5).rotate("L10").position shouldBe 95
    }

    test("Right rotation, wrap around") {
        Dial(95).rotate("R10").position shouldBe 5
        Dial(95).rotate("R5").position shouldBe 0
    }

    test("Multiple rotations") {
        val input: List<String> = listOf("L68",
                "L30",
                "R48",
                "L5",
                "R60",
                "L55",
                "L1",
                "L99",
                "R14",
                "L82"
        )
        Dial.rotate(input).position shouldBe 32
    }


// Fails with a NoSuchMethodException
//    data class Rotation(val input: String)
//    context("dial starts at 50, ends at 99, wraps around to 0") {
//        withData(
//            Rotation("R11"),
//            Rotation("R11"),
//        ) { (input) ->
//            0 shouldBe 0
//        }
//    }

})

open class Dial(position: Int) {
    val position = position
    fun rotate(rotation: String): Dial {
        if (rotation.contains("R")) {
            var newPosition = position + rotation.substring(1).toInt()
            if (newPosition >= 100) newPosition -= 100
            return Dial(newPosition)
        }
        else {
            var newPosition = position - rotation.substring(1).toInt()
            if (newPosition < 0) newPosition += 100
            return Dial(newPosition)
        }
    }

    companion object {
        fun rotate(input: List<String>): Dial {
            var currentDial = Dial(50)
            for (i in input) {
                currentDial = currentDial.rotate(i)
            }
            return currentDial
        }
    }

}

package com.pdmoore.aoc

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import javax.swing.text.Position

class Day01Tests : FunSpec ({

    // starts at 50
    // handle R
    // handle L
    // handle R past 99
    // handle L past 0
    // handle multiple turns
    // count number of times it stops on 0

    test("Right hand rotation, no wrap around") {
        Dial(50).rotate("R11") shouldBe 61
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
    fun rotate(rotation: String): Int {
        return position + rotation.substring(1).toInt()
    }

}

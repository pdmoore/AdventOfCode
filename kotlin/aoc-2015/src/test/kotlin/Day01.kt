import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

class Day01 : FunSpec({

    data class FloorDirections(val input: String)
    context("Balanced parens should end up on ground floor {0}") {
        withData(
            FloorDirections("(())"),
            FloorDirections("()")
        ) { (input) ->
            solvePart1(input) shouldBe 0
        }
    }

    context("Excess left parens end up above ground {0}") {
        withData(
            FloorDirections("((("),
            FloorDirections("(()(()("),
            FloorDirections("))(((((")
        ) { (input) ->
            solvePart1(input) shouldBe 3
        }
    }

    context("Excess right paren ends up below ground {0}") {
        withData(
            FloorDirections("())"),
            FloorDirections("))("),
        ) { (input) ->
            solvePart1(input) shouldBe -1
        }
    }

    context("Many excess right parens ends up further below ground {0}") {
        withData(
            FloorDirections(")))"),
            FloorDirections(")())())"),
        ) { (input) ->
            solvePart1(input) shouldBe -3
        }
    }

    test("part 1 solution") {
        val input = PuzzleInput.asSingleLine("./data/day01")
        solvePart1(input) shouldBe 232
    }

    test("part 2 examples, detect when basement is first entered") {
        solvePart2(")") shouldBe  1
        solvePart2("()())") shouldBe 5
    }

    test("part 2 solution") {
        val input = PuzzleInput.asSingleLine("./data/day01")
        solvePart2(input) shouldBe 1783
    }



})

    private fun solvePart2(inputLine: String): Int {
        var floor = 0
        for (i in inputLine.indices) {
            floor += upOrDown(inputLine[i])
            if (floor < 0) return i + 1
        }
        throw IllegalArgumentException("Never entered basement for $inputLine")
    }

    private fun solvePart1(inputLine: String): Int {
        var floor = 0
        inputLine.forEach { c ->
            floor += upOrDown(c)
        }
        return floor
    }

    private fun upOrDown(c: Char): Int {
        if ('(' == c) return 1
        return -1
    }
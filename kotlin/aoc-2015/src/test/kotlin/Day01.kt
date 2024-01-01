import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals

class Day01 : FunSpec({

    @ParameterizedTest(name = "Balanced parens should end up on ground floor {0}")
    @ValueSource(strings = ["(())", "()()"])
    fun `Confirm balanced parens end on ground floor` (inputLine: String) {
        assertEquals(0, solvePart1(inputLine))
    }

    @ParameterizedTest(name = "Excess left parens end up above ground {0}")
    @ValueSource(strings = ["(((", "(()(()(", "))((((("])
    fun `Confirm more left than right parens end up on positive floor {0}` (inputLine: String) {
        assertEquals(3, solvePart1(inputLine))
    }

    @ParameterizedTest(name = "Excess right paren ends up below ground {0}")
    @ValueSource(strings = ["())", "))("])
    fun `Confirm one extra right paren ends up on negative floor {0}` (inputLine: String) {
        assertEquals(-1, solvePart1(inputLine))
    }

    @ParameterizedTest(name = "Many excess right parens ends up further below ground {0}")
    @ValueSource(strings = [")))", ")())())"])
    fun `Confirm many extra right parens end up further negative {0}` (inputLine: String) {
        assertEquals(-3, solvePart1(inputLine))
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


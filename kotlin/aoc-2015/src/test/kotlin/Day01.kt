import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.io.File
import kotlin.test.assertEquals

class Day01 {

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

    // TODO - convert to kotest style testing
    // Does test class need to be separate from Impl?
    @Test
    fun part1_solution() {
        val input = KotlinInput.asSingleLine("./data/day01")
        Assertions.assertEquals(232, solvePart1(input))

    }

    @Test
    fun part2_examples() {
        assertEquals(1, solvePart2(")"))
        assertEquals(5, solvePart2("()())"))
    }

    @Test
    fun part2_solution() {
        val input = KotlinInput.asSingleLine("./data/day01")
        Assertions.assertEquals(1783, solvePart2(input))

    }

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

    // TODO - move to it's own class
    class KotlinInput {
        companion object {
            fun asSingleLine(filename: String): String  =  File(filename).readLines()[0]
        }
    }
}
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File

class Day01 {

    //TODO take a look at Kotest

    @Test
    fun part1_examples_count_floors() {
        Assertions.assertEquals(0, solvePart1("(())"))
        Assertions.assertEquals(0, solvePart1("()()"))
        Assertions.assertEquals(3, solvePart1("((("))
        Assertions.assertEquals(3, solvePart1("(()(()("))
        Assertions.assertEquals(3, solvePart1("))((((("))
        Assertions.assertEquals(-1, solvePart1("())"))
        Assertions.assertEquals(-1, solvePart1("())"))
        Assertions.assertEquals(-3, solvePart1(")))"))
        Assertions.assertEquals(-3, solvePart1(")())())"))
    }

    @Test
    fun part1_solution() {
        val input = asSingleLine("./data/day01")
        Assertions.assertEquals(232, solvePart1(input))

    }

    private fun asSingleLine(filename: String) = File(filename).readLines()[0]

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
}
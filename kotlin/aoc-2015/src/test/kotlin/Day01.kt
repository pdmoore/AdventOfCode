import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Day01 {

    //TODO take a look at Kotest

    @Test
    fun part1_examples_count_floors() {
        Assertions.assertEquals(0, solvePart1("(())"))
        Assertions.assertEquals(0, solvePart1("()()"))
        Assertions.assertEquals(3, solvePart1("((("))
        Assertions.assertEquals(3, solvePart1("(()(()("))
    }

    private fun solvePart1(inputLine: String): Int {
        var floor = 0
        for (c in inputLine) {
            floor += upOrDown(c)
        }
        return floor
    }

    private fun upOrDown(c: Char): Int {
        if ('(' == c) return 1
        return -1
    }
}
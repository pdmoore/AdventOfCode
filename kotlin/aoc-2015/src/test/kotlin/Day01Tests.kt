import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

class Day01Tests : FunSpec({

    data class FloorDirections(val input: String)
    context("Balanced parens should end up on ground floor {0}") {
        withData(
            FloorDirections("(())"),
            FloorDirections("()")
        ) { (input) ->
            Day01.solvePart1(input) shouldBe 0
        }
    }

    context("Excess left parens end up above ground {0}") {
        withData(
            FloorDirections("((("),
            FloorDirections("(()(()("),
            FloorDirections("))(((((")
        ) { (input) ->
            Day01.solvePart1(input) shouldBe 3
        }
    }

    context("Excess right paren ends up below ground {0}") {
        withData(
            FloorDirections("())"),
            FloorDirections("))("),
        ) { (input) ->
            Day01.solvePart1(input) shouldBe -1
        }
    }

    context("Many excess right parens ends up further below ground {0}") {
        withData(
            FloorDirections(")))"),
            FloorDirections(")())())"),
        ) { (input) ->
            Day01.solvePart1(input) shouldBe -3
        }
    }

    test("part 1 solution") {
        val input = PuzzleInput.asSingleLine("./data/day01")
        Day01.solvePart1(input) shouldBe 232
    }

    test("part 2 examples, detect when basement is first entered") {
        Day01.solvePart2(")") shouldBe  1
        Day01.solvePart2("()())") shouldBe 5
    }

    test("part 2 solution") {
        val input = PuzzleInput.asSingleLine("./data/day01")
        Day01.solvePart2(input) shouldBe 1783
    }
})

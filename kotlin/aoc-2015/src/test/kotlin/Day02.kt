import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

class Day02 : FunSpec( {

    test("Surface area is 2*l*w + 2*w*h + 2*h*l") {
        val inputLine = "2x3x4"
        surfaceAreaOf(inputLine) shouldBe 52
    }

    test("Smallest side of l*w*h rectangle") {
        val inputLine = "2x3x4"
        AreaOfSmallestSide(inputLine) shouldBe 6
    }

    data class SideLengths(val inputLine: String, val expected: Int)
    context("Wrapping paper needed is surface area plus area of smallest side") {
        withData(
            SideLengths("2x3x4", 58),
            SideLengths("1x1x10", 43)
        ) { (inputLine, expected) ->
            solvePart21(inputLine) shouldBe expected
        }
    }

    test("Solve part 1") {
        val input = PuzzleInput.asListOfStrings("./data/day02")
        solvePart21(input) shouldBe 1586300
    }

})

fun solvePart21(inputLine: List<String>): Int {
    var result = 0
    for (i in inputLine.indices) {
        result += solvePart21(inputLine[i])
    }
    return result
}

fun AreaOfSmallestSide(inputLine: String): Int {
    val split = inputLine.split("x")
    val l = split[0].toInt()
    val w = split[1].toInt()
    val h = split[2].toInt()
    val lByw = l*w
    val wByh = w*h
    val lByh = l*h
    return Math.min(lByw, Math.min(wByh, lByh))
}

fun surfaceAreaOf(inputLine: String): Int {
    val split = inputLine.split("x")
    val l = split[0].toInt()
    val w = split[1].toInt()
    val h = split[2].toInt()
    return (2*l*w) + (2*w*h) + (2*h*l)
}

//TODO calling this solvePart1 collides with Day01 - need to figure out
// whether to dump this in a class or have better naming
fun solvePart21(inputLine: String): Int {
    return surfaceAreaOf(inputLine) + AreaOfSmallestSide(inputLine)
}

import io.kotest.core.spec.style.FunSpec
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

})

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

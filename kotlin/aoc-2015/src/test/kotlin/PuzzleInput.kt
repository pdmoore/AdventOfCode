import java.io.File

class PuzzleInput {
    companion object {
        fun asSingleLine(filename: String): String  =  File(filename).readLines()[0]
        fun asListOfStrings(filename: String): List<String> = File(filename).readLines()
    }
}
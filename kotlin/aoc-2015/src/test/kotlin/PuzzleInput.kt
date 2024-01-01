import java.io.File

class PuzzleInput {
    companion object {
        fun asSingleLine(filename: String): String  =  File(filename).readLines()[0]
    }
}
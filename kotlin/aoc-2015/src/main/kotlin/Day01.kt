class Day01 {
    companion object {
        fun solvePart1(inputLine: String): Int {
            var floor = 0
            inputLine.forEach { c ->
                floor += upOrDown(c)
            }
            return floor
        }

        private fun upOrDown(c: Char): Int {
            if ('(' == c) return 1
            return -1
        }    }

}

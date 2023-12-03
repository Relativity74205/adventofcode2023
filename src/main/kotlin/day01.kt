private val numbers: Map<String, String> = mapOf("zero" to "0", "one" to "1", "two" to "2", "three" to "3", "four" to "4", "five" to "5", "six" to "6", "seven" to "7", "eight" to "8", "nine" to "9")

private fun findDigit(line: String): String {
    for (c in line) {
        if (c in '0'..'9') {
            return c.toString()
        }
    }

    return ""
}

private fun findDigitPlus(line: String, range: IntProgression): String {
    for (c in range) {
        if (line[c] in '0'..'9') {
            return line[c].toString()
        }
        for (number in numbers.keys) {
            if (line.substring(c).startsWith(number)) {
                return numbers.getOrElse(number) { "" }
            }
        }
    }

    return ""
}

private fun partA(lines: MutableList<String>): Int{
    var result = 0
    for (line in lines) {
        val firstDigit = findDigit(line)
        val secondDigit = findDigit(line.reversed())
        result += (firstDigit + secondDigit).toInt()
    }

    return result
}

private fun partB(lines: MutableList<String>): Int{
    var result = 0
    for (line in lines) {
        val firstDigit = findDigitPlus(line, line.indices)
        val secondDigit = findDigitPlus(line, line.length - 1 downTo 0)
        result += (firstDigit + secondDigit).toInt()
    }

    return result
}

fun main() {
    val lineList = readFile(1, false)
    val lineListDebug = readFile(1, true)
    val lineListDebugB = readFile(1, true, "B")

    val resultADebug = partA(lineListDebug)
    println("Part A Debug: $resultADebug")
    val resultA = partA(lineList)
    println("Part A: $resultA")
    val resultBDebug = partB(lineListDebugB)
    println("Part B Debug: $resultBDebug")
    val resultB = partB(lineList)
    println("Part B: $resultB")
}

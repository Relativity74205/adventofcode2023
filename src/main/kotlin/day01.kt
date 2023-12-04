private val numbers: Map<String, String> = mapOf("zero" to "0", "one" to "1", "two" to "2", "three" to "3", "four" to "4", "five" to "5", "six" to "6", "seven" to "7", "eight" to "8", "nine" to "9")

private fun findDigit(line: String): String {
    line.forEach { if (it in '0'..'9') return it.toString() }

    return ""
}

private fun findDigitPlus(line: String, range: IntProgression): String {
    for (index in range) {
        if (line[index] in '0'..'9') {
            return line[index].toString()
        }
        numbers.forEach{(numberString, number) -> if (line.substring(index).startsWith(numberString)) return number}
    }

    return ""
}

private fun partA(lines: List<String>): Int{
    var result = 0
    for (line in lines) {
        val firstDigit = findDigit(line)
        val secondDigit = findDigit(line.reversed())
        result += (firstDigit + secondDigit).toInt()
    }

    return result
}

private fun partB(lines: List<String>): Int{
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

    println("Part A Debug: ${partA(lineListDebug)}")
    println("Part A: ${partA(lineList)}")
    println("Part B Debug: ${partB(lineListDebugB)}")
    println("Part B: ${partB(lineList)}")
}

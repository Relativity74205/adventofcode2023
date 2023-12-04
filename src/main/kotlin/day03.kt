import kotlin.math.max
import kotlin.math.min

private class PartNumber (val number: Int, val x: Int, val y: Int)


private fun getNumbers(lines: List<String>): List<PartNumber> {
    val numbers = mutableListOf<PartNumber>()
    for (y in lines.indices) {
        val line = lines[y]
        var number = ""
        for (x in line.indices) {
            if (line[x] in '0'..'9') {
               number += line[x]
            } else {
                if (number != "") {
                    numbers.add(PartNumber(number.toInt(), x - number.length, y))
                    number = ""
                }
            }
        }
        if (number != "") {
            numbers.add(PartNumber(number.toInt(), line.length - number.length, y))
        }
    }
    return numbers.toList()
}

private fun checkPartNumber(number: PartNumber, lines: List<String>): Boolean {
    for (y in max(number.y - 1, 0) .. min(number.y + 1, lines.size - 1)) {
        for (x in max(number.x - 1, 0) .. min(number.x + number.number.toString().length,lines[y].length - 1)) {
            if (lines[y][x] !in '0'..'9' && lines[y][x] != '.') {
                return true
            }
        }
    }
    return false
}

private fun partA(lines: List<String>): Int {
    val numbers = getNumbers(lines)
    val partNumbers = numbers.filter { checkPartNumber(it, lines) }
    val notPartNumbers = numbers.filter { !checkPartNumber(it, lines) }
    return partNumbers.sumOf { it.number }
}

private fun partB(lines: List<String>): Int{
    return 0
}

fun main() {
    val lines = readFile(3, false)
    val linesDebug = readFile(3, true)

    println("Part A Debug: ${partA(linesDebug)}")
    println("Part A: ${partA(lines)}")
    println("Part B Debug: ${partB(linesDebug)}")
    println("Part B: ${partB(lines)}")
}

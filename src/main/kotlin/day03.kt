import kotlin.math.max
import kotlin.math.min

private class Number (val value: Int, val x: Int, val y: Int)
private class Symbol (val symbol: Char, val x: Int, val y: Int)


private fun getNumbers(lines: List<String>): Pair<List<Number>, List<Symbol>> {
    val numbers = mutableListOf<Number>()
    val symbols = mutableListOf<Symbol>()
    for (y in lines.indices) {
        val line = lines[y]
        var x = 0
        while (x < line.length ) {
            if (line[x] !in '0'..'9' && line[x] != '.') {
                symbols.add(Symbol(line[x], x, y))
            }
            if (line[x] in '0'..'9') {
                var number = line[x].toString()
                while (x + 1 < line.length && line[x + 1] in '0'..'9') {
                    number += line[x + 1]
                    x++
                }
                numbers.add(Number(number.toInt(), x - number.length + 1, y))
            }
            x++
        }
    }
    return numbers.toList() to symbols.toList()
}

private fun checkPartNumber(number: Number, lines: List<String>): Boolean {
    for (y in max(number.y - 1, 0) .. min(number.y + 1, lines.size - 1)) {
        for (x in max(number.x - 1, 0) .. min(number.x + number.value.toString().length,lines[y].length - 1)) {
            if (lines[y][x] !in '0'..'9' && lines[y][x] != '.') {
                return true
            }
        }
    }
    return false
}

private fun partA(lines: List<String>): Int {
    val (numbers, symbols) = getNumbers(lines)
    val partNumbers = numbers.filter { checkPartNumber(it, lines) }
    return partNumbers.sumOf { it.value }
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

import kotlin.math.abs

private class Number (val value: Int, val x: Int, val y: Int)
private class Symbol (val value: Char, val x: Int, val y: Int)


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

private fun getParts(symbol: Symbol, numbers: List<Number>): List<Number> {
    return numbers.filter { isNeighbor(symbol, it) }.toList()
}

private fun isNeighbor(symbol: Symbol, number: Number): Boolean {
    if (abs(symbol.y - number.y) <= 1) {
        val deltaX = symbol.x - number.x
        if (deltaX >= -1 && deltaX <= number.value.toString().length) {
            return true
        }
    }
    return false
}

private fun calcSymbolGearRatio(symbol: Symbol, numbers: List<Number>): Int {
    if (symbol.value != '*') { return 0 }
    val neighborNumbers = numbers.filter { isNeighbor(symbol, it) }.toList()
    if (neighborNumbers.size != 2) { return 0 }
    return neighborNumbers.maxOf { it.value } * neighborNumbers.minOf { it.value }
}

private fun partA(lines: List<String>): Int {
    val (numbers, symbols) = getNumbers(lines)
    val partNumbers = symbols.map{getParts(it, numbers)}.flatten().toSet()
    return partNumbers.sumOf { it.value }
}

private fun partB(lines: List<String>): Int {
    val (numbers, symbols) = getNumbers(lines)
    val gears = symbols.map{calcSymbolGearRatio(it, numbers)}
    return gears.sumOf { it }
}

fun main() {
    val lines = readFile(3, false)
    val linesDebug = readFile(3, true)

    println("Part A Debug: ${partA(linesDebug)}")
    println("Part A: ${partA(lines)}")
    println("Part B Debug: ${partB(linesDebug)}")
    println("Part B: ${partB(lines)}")
}

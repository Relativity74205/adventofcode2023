private fun parseLines(lines: List<String>): List<List<Int>> {
    return lines.map { it.split(" ").map { number -> number.toInt() } }
}

private fun processSequence(sequence: List<Int>, flagBackwards: Boolean): Int {
    if (sequence.sum() == 0) return 0

    val newSequence = mutableListOf<Int>()
    for (i in 0..<sequence.size - 1) {
        newSequence.add(sequence[i + 1] - sequence[i])
    }

    return if (flagBackwards) {
        newSequence.first() - processSequence(newSequence, true)
    } else {
        newSequence.last() + processSequence(newSequence, false)
    }
}

private fun partA(lines: List<String>): Int {
    val sequences = parseLines(lines)
    return sequences.sumOf { it.last() + processSequence(it, false) }
}

private fun partB(lines: List<String>): Int {
    val sequences = parseLines(lines)
    return sequences.sumOf { it.first() - processSequence(it, true) }
}

fun main() {
    val lines = readFile(9, false)
    val linesDebug = readFile(9, true)

    println("Part A Debug: ${partA(linesDebug)}")
    println("Part A: ${partA(lines)}")
    println("Part B Debug: ${partB(linesDebug)}")
    println("Part B: ${partB(lines)}")
}

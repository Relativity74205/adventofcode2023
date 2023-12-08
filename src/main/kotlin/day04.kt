import kotlin.math.pow

private class ScratchCard(var copies: Int, val winningNumbers: Set<Int>, val numbers: Set<Int>) {
    fun matches(): Int {
        return this.numbers.intersect(this.winningNumbers).size
    }
}


private fun parseLines(lines: List<String>): List<ScratchCard> {
    val cards = mutableListOf<ScratchCard>()
    for (line in lines) {
        val winningNumbers = line.split(": ")[1].split(" | ")[0].trim().split("  ", " ").map { it.trim().toInt() }
        val numbers = line.split(": ")[1].split(" | ")[1].trim().split("  ", " ").map { it.trim().toInt() }
        cards.add(ScratchCard(1, winningNumbers.toSet(), numbers.toSet()))
    }
    return cards.toList()
}

private fun partA(lines: List<String>): Int {
    val cards = parseLines(lines)
    return cards.sumOf { (2.0).pow(it.matches() - 1).toInt() }
}

private fun partB(lines: List<String>): Int {
    val cards = parseLines(lines)
    for (i in cards.indices) {
        val card = cards[i]
        for (j in 1..card.matches()) {
            cards[i + j].copies += card.copies
        }
    }
    return cards.sumOf { it.copies }
}

fun main() {
    val lines = readFile(4, false)
    val linesDebug = readFile(4, true)

    println("Part A Debug: ${partA(linesDebug)}")
    println("Part A: ${partA(lines)}")
    println("Part B Debug: ${partB(linesDebug)}")
    println("Part B: ${partB(lines)}")
}

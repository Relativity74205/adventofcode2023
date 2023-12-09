private enum class Card {
    A, K, Q, J, T, N9, N8, N7, N6, N5, N4, N3, N2
}

private enum class HandType{
    FiveOfAKind, FourOfAKind, FullHouse, ThreeOfAKind, TwoPair, OnePair, HighCard
}

private class Hand(val hand: String, val strength: Int, val withJoker: Boolean): Comparable<Hand> {

    private fun getCards(): List<Card> {
        return hand.map { char ->
            when(char) {
                'A' -> Card.A
                'K' -> Card.K
                'Q' -> Card.Q
                'J' -> Card.J
                'T' -> Card.T
                '9' -> Card.N9
                '8' -> Card.N8
                '7' -> Card.N7
                '6' -> Card.N6
                '5' -> Card.N5
                '4' -> Card.N4
                '3' -> Card.N3
                '2' -> Card.N2
                else -> throw Exception("Unknown card")
            }
        }
    }
    fun getHandType(): HandType {
        val cards = this.getCards().groupingBy { it }.eachCount().toMutableMap()

        if (withJoker && Card.J in cards.keys && cards.size > 1) {
            val amountJ = cards.getOrElse(Card.J) { 0 }
            cards.remove(Card.J)
            cards[cards.maxBy { it.value }.key] = cards[cards.maxBy { it.value }.key]!! + amountJ
        }

        return if (cards.values.contains(5)) {
            HandType.FiveOfAKind
        } else if (cards.values.contains(4)) {
            HandType.FourOfAKind
        } else if (cards.values.contains(3) && cards.values.contains(2)) {
            HandType.FullHouse
        } else if (cards.values.contains(3)) {
            HandType.ThreeOfAKind
        } else if (cards.values.filter { it == 2 }.size == 2) {
            HandType.TwoPair
        } else if (cards.values.contains(2)) {
            HandType.OnePair
        } else {
            HandType.HighCard
        }
    }

    override fun compareTo(other: Hand): Int {
        if (this.getHandType() != other.getHandType()) {
            return other.getHandType().ordinal - this.getHandType().ordinal
        }
        for (index in this.getCards().indices) {
            val thisCard = this.getCards()[index]
            val thisCardValue = if (thisCard == Card.J && withJoker) 100 else thisCard.ordinal
            val otherCard = other.getCards()[index]
            val otherCardValue = if (otherCard == Card.J && withJoker) 100 else otherCard.ordinal
            if (thisCardValue > otherCardValue) {
                return -1  // if less than other
            } else if (thisCardValue < otherCardValue) {
                return 1  // if greater than other
            }
        }
        return 0
    }
}

private fun parseLines(lines: List<String>, withJoker: Boolean): List<Hand> {
    return lines.map { line -> Hand(line.split(" ")[0], line.split(" ")[1].toInt(), withJoker)  }
}

private fun partA(lines: List<String>): Int {
    val hands = parseLines(lines, false)
    val handsSorted = hands.sorted()
    return handsSorted.mapIndexed { index, hand -> hand.strength * (index + 1) }.sum()
}

private fun partB(lines: List<String>): Int {
    val hands = parseLines(lines, true)
    val handsSorted = hands.sorted()
    return handsSorted.mapIndexed { index, hand -> hand.strength * (index + 1) }.sum()
}

fun main() {
    val lines = readFile(7, false)
    val linesDebug = readFile(7, true)

    println("Part A Debug: ${partA(linesDebug)}")
    println("Part A: ${partA(lines)}")  // 250120186
    println("Part B Debug: ${partB(linesDebug)}")
    println("Part B: ${partB(lines)}")  // 250665248
}

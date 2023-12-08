
private enum class CardA(val value: Int){
    A(14), K(13), Q(12), J(11), T(10), N9(9), N8(8), N7(7), N6(6), N5(5), N4(4), N3(3), N2(2)
}
private enum class CardB(val value: Int){
    A(14), K(13), Q(12), T(10), N9(9), N8(8), N7(7), N6(6), N5(5), N4(4), N3(3), N2(2), J(1)
}

private enum class HandType{
    FiveOfAKind, FourOfAKind, FullHouse, ThreeOfAKind, TwoPair, OnePair, HighCard
}

private class Hand(val hand: String, val strength: Int): Comparable<Hand> {

    private fun getCards(): List<CardA> {
        return hand.map { char ->
            when(char) {
                'A' -> CardA.A
                'K' -> CardA.K
                'Q' -> CardA.Q
                'J' -> CardA.J
                'T' -> CardA.T
                '9' -> CardA.N9
                '8' -> CardA.N8
                '7' -> CardA.N7
                '6' -> CardA.N6
                '5' -> CardA.N5
                '4' -> CardA.N4
                '3' -> CardA.N3
                '2' -> CardA.N2
                else -> throw Exception("Unknown card")
            }
        }
    }
    fun getHandType(): HandType {
        val cards = this.getCards().groupingBy { it }.eachCount()

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
            if (this.getCards()[index].value > other.getCards()[index].value) {
                return 1
            } else if (this.getCards()[index].value < other.getCards()[index].value) {
                return -1
            }
        }
        return 0
    }
}

private class HandB(val hand: String, val strength: Int): Comparable<HandB> {

    private fun getCards(): List<CardB> {
        return hand.map { char ->
            when(char) {
                'A' -> CardB.A
                'K' -> CardB.K
                'Q' -> CardB.Q
                'J' -> CardB.J
                'T' -> CardB.T
                '9' -> CardB.N9
                '8' -> CardB.N8
                '7' -> CardB.N7
                '6' -> CardB.N6
                '5' -> CardB.N5
                '4' -> CardB.N4
                '3' -> CardB.N3
                '2' -> CardB.N2
                else -> throw Exception("Unknown card")
            }
        }
    }
    fun getHandType(): HandType {
        val cards = this.getCards().groupingBy { it }.eachCount().toMutableMap()

        if (CardB.J in cards.keys && cards.size > 1) {
            val amountJ = cards.getOrElse(CardB.J) { 0 }
            cards.remove(CardB.J)
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

    override fun compareTo(other: HandB): Int {
        if (this.getHandType() != other.getHandType()) {
            return other.getHandType().ordinal - this.getHandType().ordinal
        }
        for (index in this.getCards().indices) {
            if (this.getCards()[index].value > other.getCards()[index].value) {
                return 1
            } else if (this.getCards()[index].value < other.getCards()[index].value) {
                return -1
            }
        }
        return 0
    }
}

private fun parseLines(lines: List<String>): List<Hand> {
    return lines.map { line -> Hand(line.split(" ")[0], line.split(" ")[1].toInt()) }
}

private fun parseLinesB(lines: List<String>): List<HandB> {
    return lines.map { line -> HandB(line.split(" ")[0], line.split(" ")[1].toInt()) }
}

private fun partA(lines: List<String>): Int {
    val hands = parseLines(lines)
    val handsSorted = hands.sorted()
    return handsSorted.mapIndexed { index, hand -> hand.strength * (index + 1) }.sum()
}

private fun partB(lines: List<String>): Int {
    val hands = parseLinesB(lines)
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

import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.sqrt


private class Race(val s: Double, val t: Double) {
    fun countWinScenarios(): Int {
        // we have also to take into account that the result that tStartMin/Max is exactly an integer
        val tStartMin = floor(t/2.0 - sqrt((t/2.0).pow(2) - s)) + 1
        val tStartMax = ceil(t/2.0 + sqrt((t/2.0).pow(2) - s)) - 1

        return tStartMax.toInt() - tStartMin.toInt() + 1
    }
}

private fun parseLineA(line: String): List<Int>{
    return line.split(":")[1].trim().split("\\s+".toRegex()).map { it.toInt() }
}

private fun parseLineB(line: String): Double{
    return line.split(":")[1].replace(" ", "").toDouble()
}

private fun parseLinesA(lines: List<String>): List<Race> {
    val times = parseLineA(lines[0])
    val distances = parseLineA(lines[1])
    return distances.zip(times) { s, t -> Race(s.toDouble(), t.toDouble()) }
}

private fun parseLinesB(lines: List<String>): Race {
    val time = parseLineB(lines[0])
    val distance = parseLineB(lines[1])
    return Race(distance, time)
}

private fun partA(lines: List<String>): Int {
    val races = parseLinesA(lines)
    return races.fold(1) { acc, race ->  race.countWinScenarios() * acc }
}

private fun partB(lines: List<String>): Int {
    val race = parseLinesB(lines)
    return race.countWinScenarios()
}

fun main() {
    val lines = readFile(6, false)
    val linesDebug = readFile(6, true)

    println("Part A Debug: ${partA(linesDebug)}")
    println("Part A: ${partA(lines)}")
    println("Part B Debug: ${partB(linesDebug)}")
    println("Part B: ${partB(lines)}")
}

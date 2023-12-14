import java.math.BigInteger
import kotlin.math.abs

private class GalaxyCoordinates (val x: Int, val y: Int) {
    fun distanceTo(other: GalaxyCoordinates): BigInteger {
        return abs(this.x - other.x).toBigInteger() + abs(this.y - other.y).toBigInteger()
    }
}


private fun getGalaxies(lines: List<String>, factor: Int = 2): List<GalaxyCoordinates> {
    val emptyRows = lines.mapIndexed { index, line -> if (line.all { char -> char == '.' }) index else null }.filterNotNull()
    val emptyColumns =
        lines.indices.mapNotNull { index -> if (lines.all { line -> line[index] == '.' }) index else null }

    val galaxyMap = lines.mapIndexed{y, line -> line.mapIndexed { x, char -> if (char == '#') GalaxyCoordinates(x, y) else null }.filterNotNull() }.flatten()
    val newGalaxyMap = mutableListOf<GalaxyCoordinates>()
    for (galaxy in galaxyMap) {
        val deltaXShift = emptyColumns.sumOf { emptyColumn -> if (galaxy.x > emptyColumn) factor - 1 else 0 }
        val deltaYShift = emptyRows.sumOf { emptyRow -> if (galaxy.y > emptyRow) factor - 1 else 0 }
        newGalaxyMap.add(GalaxyCoordinates(galaxy.x + deltaXShift, galaxy.y + deltaYShift))
    }

    return newGalaxyMap
}

private fun getPathLengths(galaxies: List<GalaxyCoordinates>): List<BigInteger> {
    val paths = mutableListOf<BigInteger>()
    for (index in galaxies.indices) {
        for (otherIndex in index + 1..<galaxies.size) {
            paths.add(galaxies[index].distanceTo(galaxies[otherIndex]))
        }
    }
    return paths
}

private fun partA(lines: List<String>): BigInteger {
    val galaxies = getGalaxies(lines)
    return getPathLengths(galaxies).reduce(BigInteger::add)
}

private fun partB(lines: List<String>): BigInteger {
    val galaxies = getGalaxies(lines, 1000000)
    return getPathLengths(galaxies).reduce(BigInteger::add)
}

fun main() {
    val lines = readFile(11, false)
    val linesDebug = readFile(11, true)

    println("Part A Debug: ${partA(linesDebug)}")
    println("Part A: ${partA(lines)}")
    println("Part B Debug: ${partB(linesDebug)}")
    println("Part B: ${partB(lines)}")
}

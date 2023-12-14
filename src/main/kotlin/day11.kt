import kotlin.math.abs

private class GalaxyCoordinates (val x: Int, val y: Int) {
    fun distanceTo(other: GalaxyCoordinates): Int {
        return abs(this.x - other.x) + abs(this.y - other.y)
    }
}


private fun getGalaxies(lines: List<String>): List<GalaxyCoordinates> {
    val emptyRows = lines.mapIndexed { index, line -> if (line.all { char -> char == '.' }) index else null }.filterNotNull()
    val emptyColumns =
        lines.indices.mapNotNull { index -> if (lines.all { line -> line[index] == '.' }) index else null }

    val newGalaxyMap = lines.toMutableList()
    for (i in emptyRows.sorted().reversed()) {
        newGalaxyMap.add(i, ".".repeat(lines[0].length))
    }
    for (i in emptyColumns.sorted().reversed()) {
        newGalaxyMap.forEachIndexed { index, line ->
            newGalaxyMap[index] = line.substring(0, i) + "." + line.substring(i)
        }
    }

    return newGalaxyMap.mapIndexed{x, line -> line.mapIndexed { y, char -> if (char == '#') GalaxyCoordinates(x, y) else null }.filterNotNull() }.flatten()
}

private fun partA(lines: List<String>): Int {
    val galaxies = getGalaxies(lines)
    val paths = mutableListOf<Int>()
    for (index in galaxies.indices) {
        for (otherIndex in index + 1..<galaxies.size) {
            paths.add(galaxies[index].distanceTo(galaxies[otherIndex]))
        }
    }
    return paths.sum()
}

private fun partB(lines: List<String>): Int {
    return 0
}

fun main() {
    val lines = readFile(11, false)
    val linesDebug = readFile(11, true)

    println("Part A Debug: ${partA(linesDebug)}")
    println("Part A: ${partA(lines)}")
    println("Part B Debug: ${partB(linesDebug)}")
    println("Part B: ${partB(lines)}")
}

private enum class Tile {
    V, H, NE, NW, SW, SE, GROUND, START
}

private class Coordinates(val x: Int, val y: Int) {
    fun move(direction: Tile, previousCoordinates: Coordinates): Coordinates {
        return when (direction) {
            Tile.V -> if (y - previousCoordinates.y > 0) Coordinates(x, y + 1) else Coordinates(x, y - 1)
            Tile.H -> if (x - previousCoordinates.x > 0) Coordinates(x + 1, y) else Coordinates(x - 1, y)
            Tile.NE -> if (x == previousCoordinates.x) Coordinates(x + 1, y) else Coordinates(x, y - 1)
            Tile.NW -> if (x == previousCoordinates.x) Coordinates(x - 1, y) else Coordinates(x, y - 1)
            Tile.SW -> if (x == previousCoordinates.x) Coordinates(x - 1, y) else Coordinates(x, y + 1)
            Tile.SE -> if (x == previousCoordinates.x) Coordinates(x + 1, y) else Coordinates(x, y + 1)
            Tile.GROUND -> Coordinates(x, y)
            Tile.START -> Coordinates(x, y)
        }
    }
}

private fun parseLines(lines: List<String>): List<List<Tile>> {
    val tiles = mutableListOf<List<Tile>>()
    for ( line in lines ) {
        val tileLine = mutableListOf<Tile>()
        for ( char in line ) {
            val tile = when (char) {
                '|' -> Tile.V
                '-' -> Tile.H
                'L' -> Tile.NE
                'J' -> Tile.NW
                '7' -> Tile.SW
                'F' -> Tile.SE
                '.' -> Tile.GROUND
                'S' -> Tile.START
                else -> throw Exception("Unknown tile $char")
            }
            tileLine.add(tile)
        }
        tiles.add(tileLine)
    }
    return tiles.toList()
}

private fun findStart(tiles: List<List<Tile>>): Coordinates {
    for ( (y, line) in tiles.withIndex() ) {
        for ( (x, tile) in line.withIndex() ) {
            if ( tile == Tile.START ) {
                return Coordinates(x, y)
            }
        }
    }
    throw Exception("No start found")
}

private fun findNextTile(tiles: List<List<Tile>>, startCoordinates: Coordinates): Coordinates {
    if (startCoordinates.x < tiles.size - 1) {
        if (tiles[startCoordinates.y][startCoordinates.x + 1] in listOf(Tile.H, Tile.NE, Tile.SE)) {
            return Coordinates(startCoordinates.x + 1, startCoordinates.y)
        }
    }
    if (startCoordinates.x > 0) {
        if (tiles[startCoordinates.y][startCoordinates.x - 1] in listOf(Tile.H, Tile.NW, Tile.SW)) {
            return Coordinates(startCoordinates.x - 1, startCoordinates.y)
        }
    }
    if (startCoordinates.y > 0) {
        if (tiles[startCoordinates.y + 1][startCoordinates.x] in listOf(Tile.V, Tile.NW, Tile.NE)) {
            return Coordinates(startCoordinates.x, startCoordinates.y + 1)
        }
    }
    if (startCoordinates.y < tiles.size - 1) {
        if (tiles[startCoordinates.y - 1][startCoordinates.x] in listOf(Tile.V, Tile.SW, Tile.SE)) {
            return Coordinates(startCoordinates.x, startCoordinates.y - 1)
        }
    }
    throw Exception("No next tile found")
}


private fun partA(lines: List<String>): Int {
    val tiles = parseLines(lines)
    val startCoordinates = findStart(tiles)
    val nextCoordinates = findNextTile(tiles, startCoordinates)
    var i = 1
    var previousCoordinates = startCoordinates
    var currentCoordinates = nextCoordinates
    while (tiles[currentCoordinates.y][currentCoordinates.x] != Tile.START) {
        val newCoordinates = currentCoordinates.move(tiles[currentCoordinates.y][currentCoordinates.x], previousCoordinates)
        previousCoordinates = currentCoordinates
        currentCoordinates = newCoordinates
        i++
    }

    return i / 2
}

private fun partB(lines: List<String>): Int {
    return 0
}

fun main() {
    val lines = readFile(10, false)
    val linesDebug = readFile(10, true)
    val linesDebugB = readFile(10, true, "B")

    println("Part A Debug: ${partA(linesDebug)}")
    println("Part A Debug B: ${partA(linesDebugB)}")
    println("Part A: ${partA(lines)}")
    println("Part B Debug: ${partB(linesDebug)}")
    println("Part B: ${partB(lines)}")
}

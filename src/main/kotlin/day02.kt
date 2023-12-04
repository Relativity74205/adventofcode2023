private data class Draw(val red: Int, val green: Int, val blue: Int)
private data class Game(val id: Int, val draws: List<Draw>)


private fun parseLines(lines: List<String>): List<Game> {
    val games = mutableListOf<Game>()

    for (line in lines) {
        val draws = mutableListOf<Draw>()
        val gameId = line.split(": ")[0].split(" ")[1].toInt()
        for (draw in line.split(": ")[1].split("; ")) {
            var red = 0; var green = 0; var blue = 0
            for (it in draw.split(", ")) {
                val numberShards = it.split(" ")[0].toInt()
                val colorShards = it.split(" ")[1]
                when (colorShards) {
                    "red" -> red += numberShards
                    "green" -> green += numberShards
                    "blue" -> blue += numberShards
                }
            }
            draws.add(Draw(red, green, blue))
        }
        games.add(Game(gameId, draws))
    }

    return games.toList()
}

private fun checkGame(game: Game): Int {
    for (draw in game.draws) {
        if (draw.red > 12 || draw.green > 13 || draw.blue > 14) {
            return 0
        }
    }
    return game.id
}

private fun powerGame(game: Game): Int {
    val red = game.draws.maxOf { it.red }
    val green = game.draws.maxOf { it.green }
    val blue = game.draws.maxOf { it.blue }
    return red * green * blue
}

private fun partA(lines: List<String>): Int {
    val games = parseLines(lines)
    return games.sumOf { checkGame(it) }
}

private fun partB(lines: List<String>): Int{
    val games = parseLines(lines)
    return games.sumOf { powerGame(it) }
}

fun main() {
    val lines = readFile(2, false)
    val linesDebug = readFile(2, true)

    println("Part A Debug: ${partA(linesDebug)}")
    println("Part A: ${partA(lines)}")
    println("Part B Debug: ${partB(linesDebug)}")
    println("Part B: ${partB(lines)}")
}

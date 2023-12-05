import kotlin.math.min

private class MapRange(val start: Long, val end: Long, val offset: Long) {
    fun contains(number: Long): Boolean {
        return number in start..end
    }
}

private fun createMaps(lines: List<String>): List<List<MapRange>> {
    val maps: MutableList<List<MapRange>> = mutableListOf()
    var index = 3
    val mapRanges: MutableList<MapRange> = mutableListOf()
    while (index < lines.size) {
        val line = lines[index]
        if (line == "" || index == lines.size - 1) {
            index += 2
            maps.add(mapRanges.toList())
            mapRanges.clear()
            continue
        }
        val destinationStartIndex = line.split(" ").map { it.trim().toLong() }[0]
        val sourceStartIndex = line.split(" ").map { it.trim().toLong() }[1]
        val range = line.split(" ").map { it.trim().toLong() }[2]
        mapRanges.add(MapRange(sourceStartIndex, sourceStartIndex + range - 1, destinationStartIndex - sourceStartIndex))
        index++
    }
    return maps.toList()
}


private fun sourceInMapRanges(source: Long, mapRanges: List<MapRange>): Long {
    for (mapRange in mapRanges) {
        if (mapRange.contains(source)) {
            return source + mapRange.offset
        }
    }
    return source
}


private fun partA(lines: List<String>): Long {
    val seeds = lines[0].split(": ")[1].split(" ").map { it.toLong() }
    val maps = createMaps(lines)
    val lowestLocation = seeds.minOf {
        var location = it;
        maps.map { map -> location = sourceInMapRanges(location, map); location }.last()
    }
    return lowestLocation
}

private fun numbers(seeds: List<LongProgression>) = sequence {
    for (seed in seeds) {
        for (number in seed) {
            yield(number)
        }
    }
}

private fun partB(lines: List<String>): Long {
    val maps = createMaps(lines)
    val seedsRaw = lines[0].split(": ")[1].split(" ").map { it.toLong() }
    val seeds = mutableListOf<LongProgression>()
    for (index in 0..seedsRaw.size - 2 step 2) {
        seeds.add(seedsRaw[index]..<seedsRaw[index] + seedsRaw[index + 1] - 1)
    }

    val result = numbers(seeds).fold(Long.MAX_VALUE) { acc, number ->
        var location = number
        min(acc, maps.map { map -> location = sourceInMapRanges(location, map); location }.last())
    }

    return result
}

fun main() {
    val lines = readFile(5, false)
    val linesDebug = readFile(5, true)

    println("Part A Debug: ${partA(linesDebug)}")
    println("Part A: ${partA(lines)}")
    println("Part B Debug: ${partB(linesDebug)}")
    println("Part B: ${partB(lines)}")
}

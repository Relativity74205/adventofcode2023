import java.math.BigInteger

private class Node(val name: String, val left: String, val right: String)

private fun parseLines(lines: List<String>): Map<String, Node> {
    val nodes = mutableMapOf<String, Node>()
    for (line in lines) {
        val name = line.split("=")[0].trim()
        val childrenString = line.split("=")[1].trim()
        val left = childrenString.slice(1..childrenString.length - 2).split(",")[0].trim()
        val right = childrenString.slice(1..childrenString.length - 2).split(",")[1].trim()
        nodes[name] = Node(name, left, right)
    }

    return nodes.toMap()
}

private fun findPathLength(startNode: Node, endNodeMarker: String, instructions: String, nodes: Map<String, Node>): Int {
    var count = 0
    var currentNode: Node = startNode
    while (!currentNode.name.endsWith(endNodeMarker)) {
        val char = instructions[count % instructions.length]
        currentNode = if (char == 'L') {
            nodes.getValue(currentNode.left)
        } else {
            nodes.getValue(currentNode.right)
        }
        count++
    }
    return count
}

private fun partA(lines: List<String>): Int {
    val instructions = lines[0]
    val nodes = parseLines(lines.slice(2..<lines.size))

    return findPathLength(nodes.getValue("AAA"), "ZZZ", instructions, nodes)
}

private fun partB(lines: List<String>): BigInteger {
    val instructions = lines[0]
    val nodes = parseLines(lines.slice(2..<lines.size))

    val startNodes = nodes.values.filter { it.name.endsWith("A") }
    val pathLengths = startNodes.map { findPathLength(it, "Z", instructions, nodes).toBigInteger() }

    return pathLengths.fold(1.toBigInteger()) { acc, pathLength -> lcm(acc, pathLength) }
}

fun main() {
    val lines = readFile(8, false)
    val linesDebug = readFile(8, true)
    val linesDebugB = readFile(8, true, "B")

    println("Part A Debug: ${partA(linesDebug)}")
    println("Part A: ${partA(lines)}")
    println("Part B Debug: ${partB(linesDebugB)}")
    println("Part B: ${partB(lines)}")
}

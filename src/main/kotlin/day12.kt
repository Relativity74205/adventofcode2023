private fun checkSpringGroup(springGroup: String, damagedSpringsShould: List<Int>): Boolean {
    if (springGroup.contains('?')) {
        val springGroupNew = springGroup.split("?")[0]
        val damagedSpringsIs = springGroupNew.split(".").filter{it != ""}.map { it.length }.toList()
        damagedSpringsIs.zip(damagedSpringsShould).forEach { (isLength, shouldLength) ->
            if (isLength > shouldLength) {
                return false
            }
        }

        return true
    } else {
        val damagedSpringsIs = springGroup.split(".").filter{it != ""}.map { it.length }.toList()
        damagedSpringsIs.zip(damagedSpringsShould).forEach { (isLength, shouldLength) ->
            if (isLength != shouldLength) {
                return false
            }
        }
        return damagedSpringsIs.size == damagedSpringsShould.size
    }

}

private fun processSprings(springGroups: List<String>, finished: MutableList<String>, damagedSprings: List<Int>): MutableList<String> {
    val newSpringGroups = mutableListOf<String>()
    for (springGroup in springGroups) {
        if (springGroup.all{it != '?'} ) {
            finished.add(springGroup)
            continue
        }

        val index = springGroup.indexOf('?')
        var newSpringGroupList = springGroup.toMutableList()
        newSpringGroupList[index] = '.'
        var newSpringGroup = newSpringGroupList.joinToString("")
        if (checkSpringGroup(newSpringGroup, damagedSprings)) {
            newSpringGroups.add(newSpringGroup)
        }
        newSpringGroupList = springGroup.toMutableList()
        newSpringGroupList[index] = '#'
        newSpringGroup = newSpringGroupList.joinToString("")
        if (checkSpringGroup(newSpringGroup, damagedSprings)) {
            newSpringGroups.add(newSpringGroup)
        }
    }
    if (newSpringGroups.isNotEmpty()) {
        processSprings(newSpringGroups, finished, damagedSprings)
    }

    return finished
}

private fun partA(lines: List<String>): Int {
    return lines.sumOf {line ->
        val damagedSpringGroups = line.split(" ")[1].split(",").map { it.toInt() }
        val springs = line.split(" ")[0]
        val amountCombinations = processSprings(listOf(springs), mutableListOf(), damagedSpringGroups).size
        amountCombinations
    }
}

private fun partB(lines: List<String>): Int {
    return 0
}

fun main() {
    val lines = readFile(12, false)
    val linesDebug = readFile(12, true)

    println("Part A Debug: ${partA(linesDebug)}")
    println("Part A: ${partA(lines)}")
    println("Part B Debug: ${partB(linesDebug)}")
    println("Part B: ${partB(lines)}")
}

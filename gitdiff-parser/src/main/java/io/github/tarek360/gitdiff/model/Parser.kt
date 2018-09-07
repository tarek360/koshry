package io.github.tarek360.gitdiff.model

class Parser {

  data class StartCount(val start: Int, val count: Int)

  fun parse(rawLines: List<String>): Lines {
    val deletedLines = mutableListOf<Line>()
    val addedLines = mutableListOf<Line>()

    var i = 0
    while (i < rawLines.size) {
      val line = rawLines[i]

      if (line.startsWith("@@ ")) {

        val hunk = extractHunk(line).split(" ")

        val deletedHunk = parseStartCount(hunk[0])
        val addedHunk = parseStartCount(hunk[1])

        val deletedLinesStartFrom = i + 1
        val addedLinesStartFrom = deletedLinesStartFrom + deletedHunk.count

        val deletedLinesEndTo = deletedLinesStartFrom + deletedHunk.count
        val addedLinesEndTo = addedLinesStartFrom + addedHunk.count

        addRawLinesToLines(deletedHunk.start, rawLines, deletedLinesStartFrom, deletedLinesEndTo, deletedLines)
        addRawLinesToLines(addedHunk.start, rawLines, addedLinesStartFrom, addedLinesEndTo, addedLines)

        i = addedLinesEndTo
      } else {
        i++
      }
    }

    return Lines(addedLines, deletedLines)
  }

  private fun addRawLinesToLines(firstLineNumber: Int, rawLines: List<String>, from: Int, to: Int,
      lines: MutableList<Line>) {
    for ((index, i) in (from until to).withIndex()) {
      lines.add(Line(rawLines[i], firstLineNumber + index))
    }
  }

  fun parseStartCount(rawStartCount: String): StartCount {
    val splitted = rawStartCount.split(',')

    val start = if (splitted.isNotEmpty()) {
      Integer.parseInt(splitted[0])
    } else {
      0
    }

    val count = if (splitted.size > 1) {
      Integer.parseInt(splitted[1])
    } else {
      1 // Git doesn't put 1 as a count in case of only one line.
    }

    return StartCount(start, count)
  }

  fun extractHunk(line: String): String {
    val last = line.indexOf(" @@")
    return line.substring(4, last)
  }
}

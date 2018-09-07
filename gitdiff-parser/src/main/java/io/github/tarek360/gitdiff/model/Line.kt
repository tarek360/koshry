package io.github.tarek360.gitdiff.model

data class Line(val text: String, val number: Int = 0)
data class Lines(val addedLines: List<Line>, val deletedLines: List<Line>)

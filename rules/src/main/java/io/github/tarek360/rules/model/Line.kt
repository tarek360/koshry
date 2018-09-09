package io.github.tarek360.rules.model

import io.github.tarek360.gitdiff.Line

class Line(line: Line) {
    val text: String = line.text
    val number: Int = line.number
}

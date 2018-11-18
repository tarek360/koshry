package io.github.tarek360.rules.coverage

interface CsvParser {
    fun parse(csvFilePath: String): ArrayList<ClassCoverage>
}

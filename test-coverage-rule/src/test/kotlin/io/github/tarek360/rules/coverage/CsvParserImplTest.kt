package io.github.tarek360.rules.coverage

import io.github.tarek360.core.mustEqual
import io.github.tarek360.core.mustHaveSize
import org.junit.Test

import org.junit.Assert.*

class CsvParserImplTest {

    @Test
    fun parse() {

        // Arrange
        val csvParserImpl = CsvParserImpl()
        val url = Thread.currentThread().contextClassLoader.getResource("jacoco.csv")

        // Act
        val result = csvParserImpl.parse(url.path)

        // Assert
        result mustHaveSize 12

        result[0].classPackage mustEqual "io.github.tarek360.magic"
        result[0].className mustEqual "Lines"
        result[0].classPath mustEqual "io/github/tarek360/magic/Lines"
        result[0].coveredBranches mustEqual 50

        result[1].classPackage mustEqual "io.github.tarek360.gitdiff"
        result[1].className mustEqual "GitFileImpl"
        result[1].classPath mustEqual "io/github/tarek360/gitdiff/GitFileImpl"
        result[1].coveredBranches mustEqual 80

        result[2].classPackage mustEqual "io.github.tarek360.gitdiff"
        result[2].className mustEqual "Parser.StartCount"
        result[2].classPath mustEqual "io/github/tarek360/gitdiff/Parser\$StartCount"
        result[2].coveredBranches mustEqual 100

        result[3].classPackage mustEqual "io.github.tarek360.gitdiff"
        result[3].className mustEqual "Controller"
        result[3].classPath mustEqual "io/github/tarek360/gitdiff/Controller"
        result[3].coveredBranches mustEqual 0


    }
}
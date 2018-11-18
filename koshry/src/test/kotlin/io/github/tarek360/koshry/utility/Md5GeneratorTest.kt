package io.github.tarek360.koshry.utility

import io.github.tarek360.core.mustEqual
import org.junit.Before
import org.junit.Test

class Md5GeneratorTest {

    private lateinit var md5Generator: Md5Generator

    @Before
    fun setup() {
        md5Generator = Md5Generator()
    }

    @Test
    fun test1() {

        // Arrange
        val text = "test-coverage-rule/src/main/java/io/github/tarek360/rules/coverage/JacocoCoverageRule.kt"

        // Act
        val md5 = md5Generator.getMd5(text)

        // Assert
        md5 mustEqual "50a1ae3c9c4d8b7726afcebf58a7a3c2"
    }

    @Test
    fun test2() {

        // Arrange
        val text = "rules-test/src/main/java/io/github/tarek360/rules/test/TestRule.kt"

        // Act
        val md5 = md5Generator.getMd5(text)

        // Assert
        md5 mustEqual "0311577da4bcf76a7e3c9d7fdc66f212"
    }
}

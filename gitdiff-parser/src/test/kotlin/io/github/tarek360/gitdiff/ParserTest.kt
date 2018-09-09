package io.github.tarek360.gitdiff

import io.github.tarek360.core.mustEqual
import io.github.tarek360.core.mustHaveSize
import org.junit.Test

class ParserTest {

  @Test
  fun parse() {
    //Arrange
    val parser = Parser()

    //Act
    val lines = parser.parse(getRawLines())

    //Assert

    lines.deletedLines mustHaveSize 3
    lines.addedLines mustHaveSize 4


    lines.deletedLines[0].number mustEqual 22
    lines.deletedLines[1].number mustEqual 23
    lines.deletedLines[2].number mustEqual 24

    lines.addedLines[0].number mustEqual 22
    lines.addedLines[1].number mustEqual 23
    lines.addedLines[2].number mustEqual 28
    lines.addedLines[3].number mustEqual 29
  }

  @Test
  fun extractHunk() {
    //Arrange
    val parser = Parser()

    //Act
    val hunk = parser.extractHunk("@@ -28,0 +28,2 @@ java {")

    //Assert
    hunk mustEqual "28,0 +28,2"
  }

  @Test
  fun parseStartCount() {
    //Arrange
    val parser = Parser()

    //Act
    val deleteStartCount = parser.parseStartCount("7,0")
    val addedStartCount = parser.parseStartCount("+28,2")

    val deleteStartWithOneCount = parser.parseStartCount("245")

    //Assert
    deleteStartCount.start mustEqual 7
    deleteStartCount.count mustEqual 0

    addedStartCount.start mustEqual 28
    addedStartCount.count mustEqual 2

    deleteStartWithOneCount.start mustEqual 245
    deleteStartWithOneCount.count mustEqual 1

  }


  private fun getRawLines(): List<String> {
    val textLines = arrayListOf<String>()
    textLines.add("diff-- git a / gitdiff / build.gradle.kts b / gitdiff / build . gradle . kts")
    textLines.add("index 516a3a0..8e0fa1b 100644")
    textLines.add("---a / gitdiff / build.gradle.kts")
    textLines.add("+++b / gitdiff / build.gradle.kts")
    textLines.add("@@ -22,3 22,2 @@ dependencies {")
    textLines.add("-implementation(\"io.github.microutils:kotlin-logging:1.5.9\")")
    textLines.add("-implementation(\"org.slf4j:slf4j-simple:1.6.1\")")
    textLines.add("-")
    textLines.add("+implementation(project(\":gitdiff-provider\"))")
    textLines.add("+  //i am 23")
    textLines.add("@@ -28,0 +28,2 @@ java {")
    textLines.add("+  //i am 28")
    textLines.add("+  //i am 29")
    return textLines
  }


}
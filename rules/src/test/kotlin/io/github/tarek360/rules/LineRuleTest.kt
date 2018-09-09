package io.github.tarek360.rules

import io.github.tarek360.gitdiff.Line
import io.github.tarek360.rules.report.Issue
import io.github.tarek360.rules.report.Level
import io.github.tarek360.rules.test.testRule
import org.junit.Test

class LineRuleTest {

    @Test
    fun test() {

        // Arrange
        val rule = lineRule {
            condition = { file, line ->
                file.path.startsWith("core/network")
                        && line.text.contains("make a crash")
            }
            reportTitle = "Don't create bugs."
            issueLevel = Level.ERROR
        }

        val linesWithIssue = listOf(
                Line(text = "this line will make a crash", number = 3),
                Line(text = "this line is safe", number = 5)
        )

        val linesWithoutIssue = listOf(
                Line(text = "this line is safe", number = 3),
                Line(text = "this line is safe as well", number = 5)
        )

        val expectedIssue1 = Issue(
                msg = "Line --> **3** , File --> **core/network/Call.java**",
                level = Level.ERROR,
                filePath = "core/network/Call.java",
                lineNumber = 3)

        val expectedIssue2 = Issue(
                msg = "Line --> **3** , File --> **core/network/http/Request.java**",
                level = Level.ERROR,
                filePath = "core/network/http/Request.java",
                lineNumber = 3)

        testRule(rule)
                .withAddedFile(file = "core/network/Call.java", addedLines = linesWithIssue)
                .withAddedFile("core/network/http/Request.java", addedLines = linesWithIssue)
                .withAddedFile(file = "core/NewFeature3.java", addedLines = linesWithIssue)
                .withModifiedFile(file = "core/network/OldFeature.java", addedLines = linesWithoutIssue)
                .withDeletedFile(file = "core/network/OutdatedFeatureActivity.java")

                // Act
                .apply()

                // Assert
                .shouldHasIssue(expectedIssue = expectedIssue1)
                .shouldHasIssue(expectedIssue = expectedIssue2)
                .shouldHasIssuesCount(expectedCount = 2)
    }
}

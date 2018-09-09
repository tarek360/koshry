package io.github.tarek360.rules

import io.github.tarek360.rules.report.Issue
import io.github.tarek360.rules.report.Level
import io.github.tarek360.rules.test.testRule
import org.junit.Test

class ProtectedFilesRuleTest {

    @Test
    fun test() {

        // Arrange
        val rule = protectedFileRule {
            reportTitle = "Files are protected and can't be modified, ask @tarek360 to modify"
            issueLevel = Level.WARN
            files {
                filePath = "secured/CriticalFile1.txt"
                filePath = "CriticalFile2"
            }
        }

        val expectedIssue1 = Issue(
                msg = "File --> **secured/CriticalFile1.txt**",
                level = Level.WARN,
                filePath = "secured/CriticalFile1.txt")

        val expectedIssue2 = Issue(
                msg = "File --> **CriticalFile2**",
                level = Level.WARN,
                filePath = "CriticalFile2")

        testRule(rule)
                .withModifiedFile(file = "secured/CriticalFile1.txt")
                .withModifiedFile(file = "Feature1.kt")
                .withModifiedFile(file = "Feature2.kt")
                .withDeletedFile(file = "CriticalFile2")
                .withAddedFile(file = "NewFile.java")

                // Act
                .apply()

                // Assert
                .shouldHasIssue(expectedIssue = expectedIssue1)
                .shouldHasIssue(expectedIssue = expectedIssue2)
                .shouldHasIssuesCount(expectedCount = 2)
    }
}

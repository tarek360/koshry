package io.github.tarek360.rules

import io.github.tarek360.rules.core.Issue
import io.github.tarek360.rules.core.Level
import io.github.tarek360.rules.test.testRule
import org.junit.Test

class FileRuleTest {

    @Test
    fun test() {

        // Arrange
        val rule = fileRule {
            condition = { file ->
                file.isAdded && file.name.endsWith(".java")
            }
            reportTitle = "Don't add new Java files, use Kotlin instead."
            issueLevel = Level.ERROR()
        }


        val expectedIssue = Issue(
                msg = "File --> **NewFeatureActivity.java**",
                level = Level.ERROR(),
                filePath = "NewFeatureActivity.java")

        testRule(rule)
                .withAddedFile(file = "NewFeatureActivity.java")
                .withModifiedFile(file = "OldFeatureActivity.java")
                .withDeletedFile(file = "OutdatedFeatureActivity.java")

                // Act
                .apply()

                // Assert
                .assertHasIssue(expectedIssue = expectedIssue)
                .assertIssuesCount(expectedCount = 1)
    }

    @Test
    fun test_noIssues() {

        // Arrange
        val rule = fileRule {
            condition = { file ->
                file.isAdded && file.name.endsWith(".java")
            }
            reportTitle = "Don't add new Java files, use Kotlin instead."
            issueLevel = Level.ERROR()
        }

        testRule(rule)
                .withModifiedFile(file = "OldFeatureActivity.java")
                .withDeletedFile(file = "OutdatedFeatureActivity.java")

                // Act
                .apply()

                // Assert
                .assertNoIssues()
    }



}

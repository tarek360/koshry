package io.github.tarek360.rules

import io.github.tarek360.rules.core.Issue
import io.github.tarek360.rules.core.Level
import io.github.tarek360.rules.core.PullRequest
import io.github.tarek360.rules.test.testRule
import org.junit.Test

class ProtectedFilesRuleTest {

    @Test
    fun test() {

        // Arrange
        val rule = protectedFileRule {
            reportTitle = "Files are protected and can't be modified, ask @tarek360 to modify"
            issueLevel = Level.WARN()
            files {
                filePath = "secured/CriticalFile1.txt"
                filePath = "CriticalFile2"
            }
        }

        val expectedIssue1 = Issue(
                msg = "File --> **secured/CriticalFile1.txt**",
                level = Level.WARN(),
                filePath = "secured/CriticalFile1.txt")

        val expectedIssue2 = Issue(
                msg = "File --> **CriticalFile2**",
                level = Level.WARN(),
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
                .assertHasIssue(expectedIssue = expectedIssue1)
                .assertHasIssue(expectedIssue = expectedIssue2)
                .assertIssuesCount(expectedCount = 2)
    }

    @Test
    fun test_noIssues() {
        // Arrange
        val rule = protectedFileRule {
            reportTitle = "Files are protected and can't be modified, ask @tarek360 to modify"
            issueLevel = Level.ERROR()
            files {
                filePath = "CriticalFile"
            }
        }

        testRule(rule)
                .withModifiedFile(file = "AnyFile")
                .withDeletedFile(file = "UnnecessaryFile")

                // Act
                .apply()

                // Assert
                .assertNoIssues()
    }

    @Test
    fun test_excludeAuthors() {
        // Arrange
        val rule = protectedFileRule {
            reportTitle = "Files are protected and can't be modified, ask @tarek360 to modify"
            issueLevel = Level.WARN()
            files {
                filePath = "CriticalFile"
            }
            excludeAuthors {
                author = "karim"
                author = "ahmed"
                author = "david"
            }
        }

        testRule(rule)
                .withModifiedFile(file = "CriticalFile")
                .withPullRequest(PullRequest(null, null, "ahmed"))

                // Act
                .apply()

                // Assert
                .assertNoIssues()
    }

    @Test
    fun test_excludeAuthors_nullPullRequestAuthor() {
        // Arrange
        val rule = protectedFileRule {
            reportTitle = "Files are protected and can't be modified, ask @tarek360 to modify"
            issueLevel = Level.ERROR()
            files {
                filePath = "CriticalFile"
            }
            excludeAuthors {
                author = "karim"
                author = "ahmed"
                author = "david"
            }
        }

        val expectedIssue = Issue(
                msg = "File --> **CriticalFile**",
                level = Level.ERROR(),
                filePath = "CriticalFile")

        testRule(rule)
                .withModifiedFile(file = "CriticalFile")
                .withPullRequest(PullRequest(null, null, null))

                // Act
                .apply()

                // Assert
                .assertHasIssue(expectedIssue)
    }
}

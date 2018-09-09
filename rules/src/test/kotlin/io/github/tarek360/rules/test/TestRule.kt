package io.github.tarek360.rules.test

import io.github.tarek360.gitdiff.GitFile
import io.github.tarek360.gitdiff.Line
import io.github.tarek360.rules.Rule
import io.github.tarek360.rules.report.Issue
import io.github.tarek360.rules.report.Report
import org.hamcrest.CoreMatchers.hasItem
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat

fun testRule(rule: Rule): TestRule = TestRule(rule)

class TestRule internal constructor(private val rule: Rule) {

    private val addedFiles = mutableListOf<TestGitFile>()
    private val deletedFiles = mutableListOf<TestGitFile>()
    private val modifiedFiles = mutableListOf<TestGitFile>()
    private var report: Report? = null

    fun withAddedFile(file: String, addedLines: List<Line> = emptyList())
            : TestRule {
        addedFiles.add(TestGitFile(file, GitFile.Type.ADDED, addedLines, emptyList()))
        return this
    }

    fun withDeletedFile(file: String, deletedLines: List<Line> = emptyList())
            : TestRule {
        deletedFiles.add(TestGitFile(file, GitFile.Type.DELETED, emptyList(), deletedLines))
        return this
    }

    fun withModifiedFile(file: String, addedLines: List<Line> = emptyList(), deletedLines: List<Line> = emptyList())
            : TestRule {
        modifiedFiles.add(TestGitFile(file, GitFile.Type.MODIFIED, addedLines, deletedLines))
        return this
    }

    fun apply(): TestRule {
        val gitDiffTest = TestGitDiff(addedFiles, deletedFiles, modifiedFiles)
        report = rule.apply(gitDiffTest)
        return this
    }

    fun shouldHasIssue(expectedIssue: Issue): TestRule {

        val issues = report?.issues

        if (issues == null) {
            throw RuntimeException()
        } else {
            assertThat(issues, hasItem(expectedIssue))
        }
        return this
    }

    fun shouldHasIssuesCount(expectedCount: Int): TestRule {

        val issues = report?.issues

        if (issues == null) {
            throw RuntimeException()
        } else {
            assertEquals("unexpected size", expectedCount, issues.size)
        }
        return this
    }
}

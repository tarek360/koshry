package io.github.tarek360.koshry

import com.nhaarman.mockitokotlin2.whenever
import io.github.tarek360.core.mustEqual
import io.github.tarek360.rules.core.Ci
import io.github.tarek360.rules.core.PullRequest
import io.github.tarek360.rules.core.Rule
import io.github.tarek360.rules.core.Report
import io.github.tarek360.rules.core.Level
import io.github.tarek360.rules.core.Issue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RulesManTest {

    @Mock
    private lateinit var reportsAggregator: ReportsAggregator

    private val rulesMan by lazy {
        val pullRequest = PullRequest("","","", "", "", arrayListOf("bug", "fix"))
        val ci = Ci("",0,"", "0")
        RulesMan(ci, pullRequest, reportsAggregator)
    }

    private val baseSha = "abc"
    private val headSha = "def"

    @Test
    fun test() {
        // Arrange
        val report1 = getFailedReport()
        val report2 = getPassedReport()
        val rule1 = mock(Rule::class.java)
        val rule2 = mock(Rule::class.java)
        whenever(rule1.run()).thenReturn(report1)
        whenever(rule2.run()).thenReturn(report2)

        val markdown = "## my markdown"
        whenever(reportsAggregator.aggregate(listOf(report1, report2))).thenReturn(markdown)

        // Act
        val comment = rulesMan.applyRules(baseSha, headSha, listOf(rule1, rule2))

        // Assert
        comment.isFailed mustEqual true
        comment.msg mustEqual markdown
    }

    @Test
    fun test_withNullReport() {
        // Arrange
        val rule = mock(Rule::class.java)
        whenever(rule.run()).thenReturn(null)

        val markdown = "## passed"
        whenever(reportsAggregator.aggregate(emptyList())).thenReturn(markdown)

        // Act
        val comment = rulesMan.applyRules(baseSha, headSha, listOf(rule))

        // Assert
        comment.isFailed mustEqual false
        comment.msg mustEqual markdown
    }

    private fun getPassedReport(): Report = getReport(Level.INFO())

    private fun getFailedReport(): Report = getReport(Level.ERROR())

    private fun getReport(level: Level): Report {
        val issue1 = Issue("issue 1", level)
        val issue2 = Issue("issue 2", Level.INFO())
        return Report("Report", issues = arrayListOf(issue1, issue2))
    }
}

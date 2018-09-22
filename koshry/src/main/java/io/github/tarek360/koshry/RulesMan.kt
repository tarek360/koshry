package io.github.tarek360.koshry

import io.github.tarek360.gitdiff.GitDiffProvider
import io.github.tarek360.githost.Comment
import io.github.tarek360.rules.core.Issue
import io.github.tarek360.rules.core.Level
import io.github.tarek360.rules.core.Rule

class RulesMan(private val reportsAggregator: ReportsAggregator) {

    fun applyRules(baseSha: String,
                   headSha: String,
                   rules: List<Rule>)
            : Comment {

        var shouldFail = false
        val gitDiff = GitDiffProvider.provide(baseSha, headSha)

        val reports = rules.mapNotNull { rule ->
            rule.init(gitDiff)
            val report = rule.run()
            if (!shouldFail) {
                report?.let {
                    shouldFail = hasFailedIssue(report.issues)
                }
            }
            report
        }
        val markDownComment = reportsAggregator.aggregate(reports)
        return Comment(markDownComment, shouldFail)
    }


    private fun hasFailedIssue(issues: MutableList<Issue>): Boolean {
        for (issue in issues) {
            if (issue.level is Level.ERROR) {
                return true
            }
        }
        return false
    }

}
package io.github.tarek360.rules

import io.github.tarek360.gitdiff.GitDiff
import io.github.tarek360.gitdiff.GitFile
import io.github.tarek360.rules.FileRule.FileRuleBuilder
import io.github.tarek360.rules.core.*
import io.github.tarek360.rules.model.File
import io.github.tarek360.rules.core.Level.INFO

class FileRule private constructor(
        var condition: (File) -> Boolean,
        private var reportTitle: String,
        private var issueLevel: Level
) : Rule {

    private lateinit var report: Report

    override fun apply(gitDiff: GitDiff): Report? {
        report = Report(msgTitle = reportTitle)
        applyToFiles(gitDiff.getAddedFiles())
        applyToFiles(gitDiff.getModifiedFiles())
        return report.takeIf {
            report.issues.isNotEmpty()
        }
    }

    private fun applyToFiles(gitFiles: List<GitFile>) {
        gitFiles.forEach { file ->
            if (condition(File(file))) {
                val msg = "File --> **${file.path}**"
                report.issues.add(Issue(msg = msg, level = issueLevel, filePath = file.path))
            }
        }
    }

    @RuleDsl
    class FileRuleBuilder {
        lateinit var condition: (File) -> Boolean // Required
        var reportTitle = "File Rule" // Optional
        var issueLevel: Level = INFO // Optional

        fun build(): FileRule = FileRule(condition, reportTitle, issueLevel)
    }
}


fun fileRule(block: FileRuleBuilder.() -> Unit): FileRule = FileRuleBuilder().apply(block).build()

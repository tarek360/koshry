package io.github.tarek360.rules

import io.github.tarek360.gitdiff.GitFile
import io.github.tarek360.rules.ProtectedFilesRule.ProtectedFileRuleBuilder
import io.github.tarek360.rules.core.*
import io.github.tarek360.rules.core.Level.INFO

class ProtectedFilesRule private constructor(
        private var protectedFiles: ArrayList<String>,
        private var reportTitle: String,
        private var issueLevel: Level
) : Rule() {

    private lateinit var report: Report

    override fun run(): Report? {
        report = Report(msgTitle = reportTitle)
        applyToFiles(gitDiff.getModifiedFiles())
        applyToFiles(gitDiff.getDeletedFiles())
        return report.takeIf {
            report.issues.isNotEmpty()
        }
    }

    private fun applyToFiles(gitFiles: List<GitFile>) {
        gitFiles.forEach { file ->
            protectedFiles.forEach { protectedFile ->
                if (protectedFile == file.path) {
                    val msg = "File --> **${file.path}**"
                    report.issues.add(Issue(msg = msg, level = issueLevel, filePath = file.path))
                }
            }
        }
    }

    @RuleDsl
    class ProtectedFileRuleBuilder {

        private val files = ArrayList<String>()

        fun files(block: FILES.() -> Unit) {
            files.addAll(FILES().apply(block).build())
        }

        var reportTitle = "Protected File" // Optional
        var issueLevel: Level = INFO() // Optional

        fun build(): ProtectedFilesRule = ProtectedFilesRule(files, reportTitle, issueLevel)
    }

    @RuleDsl
    class FILES {
        private val files = mutableListOf<String>()

        private var tmpFile: String? = null

        var filePath: String
            get() = tmpFile ?: throw UninitializedPropertyAccessException("property \"file\" has not been initialized")
            set(value) {
                tmpFile = value
                files.add(value)
            }

        fun build(): List<String> = files
    }

}


fun protectedFileRule(block: ProtectedFileRuleBuilder.() -> Unit): ProtectedFilesRule
        = ProtectedFileRuleBuilder().apply(block).build()

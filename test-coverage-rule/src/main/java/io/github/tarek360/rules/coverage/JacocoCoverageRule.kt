package io.github.tarek360.rules.coverage

import io.github.tarek360.core.filterThenMapIfNotNull
import io.github.tarek360.gitdiff.GitDiff
import io.github.tarek360.gitdiff.GitFile
import io.github.tarek360.rules.coverage.JacocoCoverageRule.JacocoCoverageRuleBuilder
import io.github.tarek360.rules.core.Issue
import io.github.tarek360.rules.core.Level.ERROR
import io.github.tarek360.rules.core.Level.INFO
import io.github.tarek360.rules.core.Report
import io.github.tarek360.rules.core.Rule
import io.github.tarek360.rules.core.RuleDsl

class JacocoCoverageRule internal constructor(
        private var classCoverageThreshold: Int,
        private var csvFilePath: String,
        private var htmlFilePath: String?,
        private val csvParser : CsvParser
) : Rule {


    companion object {
        const val REPORT_TITLE = "JaCoCo Coverage Report"
        const val COVERAGE_TITLE = "Coverage"
    }

    private lateinit var report: Report
    private lateinit var classesCoverage: ArrayList<ClassCoverage>

    private val separator = System.getProperty("file.separator")

    override fun apply(gitDiff: GitDiff): Report? {
        report = Report(REPORT_TITLE, COVERAGE_TITLE)
        classesCoverage = csvParser.parse(csvFilePath)

        applyToFiles(gitDiff.getAddedFiles())
        applyToFiles(gitDiff.getModifiedFiles())
        return report.takeIf {
            report.issues.isNotEmpty()
        }
    }

    private fun applyToFiles(gitFiles: List<GitFile>) {

        gitFiles.filterThenMapIfNotNull { file ->

            val inKotlinFile = file.path.split("${separator}kotlin$separator")
            val inJavaFile = file.path.split("${separator}java$separator")

            when {
                inKotlinFile.size > 1 -> removeExtension(inKotlinFile[1])
                inJavaFile.size > 1 -> removeExtension(inJavaFile[1])
                else -> {
                    null
                }
            }

        }.forEach { gitFilePath ->

            classesCoverage.forEach { classCoverage ->

                if (classCoverage.filePath == gitFilePath || classCoverage.filePath.startsWith("$gitFilePath\$")) {
                    //Inner class
                    addIssue(classCoverage, gitFilePath)
                }
            }
        }
    }

    private fun addIssue(classCoverage: ClassCoverage, gitFilePath: String) {
        val msg = "**${classCoverage.filePath}**"
        val coverage = "**${classCoverage.coveredBranches}%**"

        val name = classCoverage.fileName.replace('.','$')

        val filePath = if (htmlFilePath != null) {
            "$htmlFilePath$separator${classCoverage.classPackage}$separator$name.html"
        } else {
            "${classCoverage.classPackage}$separator$name.html"
        }

        when {
            classCoverage.coveredBranches in 95..100 ->
                report.issues.add(Issue(level = INFO("🏆"), filePath = filePath, msg = msg, description = coverage))

            classCoverage.coveredBranches >= classCoverageThreshold ->
                report.issues.add(Issue(level = INFO("✅"), filePath = filePath, msg = msg, description = coverage))

            classCoverage.coveredBranches < classCoverageThreshold ->
                report.issues.add(Issue(level = ERROR("💣"), filePath = filePath, msg = msg, description = coverage))

        }
    }

    private fun removeExtension(file: String): String {
        val extensionIndex = file.lastIndexOf(".")
        return if (extensionIndex == -1) file else file.substring(0, extensionIndex)
    }

    @RuleDsl
    class JacocoCoverageRuleBuilder {

        var classCoverageThreshold: Int = 75
        lateinit var csvFilePath: String // Required
        var htmlFilePath: String? = null

        fun build(): JacocoCoverageRule {
            val csvParser = CsvParserImpl()
            return JacocoCoverageRule(classCoverageThreshold, csvFilePath, htmlFilePath, csvParser)
        }
    }
}


fun jacocoCoverageRule(block: JacocoCoverageRuleBuilder.() -> Unit): JacocoCoverageRule = JacocoCoverageRuleBuilder().apply(block).build()

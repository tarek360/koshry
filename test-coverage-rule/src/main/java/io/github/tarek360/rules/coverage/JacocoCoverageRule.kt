package io.github.tarek360.rules.coverage

import io.github.tarek360.core.bold
import io.github.tarek360.core.filterThenMapIfNotNull
import io.github.tarek360.gitdiff.GitFile
import io.github.tarek360.rules.core.*
import io.github.tarek360.rules.coverage.JacocoCoverageRule.JacocoCoverageRuleBuilder
import io.github.tarek360.rules.core.Level.ERROR
import io.github.tarek360.rules.core.Level.INFO

class JacocoCoverageRule internal constructor(
        private var classCoverageThreshold: Int,
        private var csvFilePath: String,
        private var htmlFilePath: String?,
        private val csvParser: CsvParser
) : Rule() {


    companion object {
        const val REPORT_TITLE = "JaCoCo Coverage Report"
        const val COVERAGE_TITLE = "Coverage"
        const val PROJECT_TOTAL_COVERAGE_TITLE = "Project Total Coverage"
    }

    private lateinit var report: Report
    private lateinit var classesCoverage: ArrayList<ClassCoverage>

    private val separator = System.getProperty("file.separator")

    override fun run(): Report? {
        report = Report(REPORT_TITLE, COVERAGE_TITLE)
        classesCoverage = csvParser.parse(csvFilePath)

        checkFiles(gitDiff.getAddedFiles())
        checkFiles(gitDiff.getModifiedFiles())

        addTotalCoverage(classesCoverage.sumBy { it.coveredBranches } / classesCoverage.size)

        return report.takeIf {
            report.issues.isNotEmpty()
        }
    }

    private fun checkFiles(gitFiles: List<GitFile>) {

        val touchedClasses = gitFiles.filterThenMapIfNotNull { file ->

            val inKotlinFile = file.path.split("${separator}kotlin$separator")
            val inJavaFile = file.path.split("${separator}java$separator")

            when {
                inKotlinFile.size > 1 -> removeExtension(inKotlinFile[1])
                inJavaFile.size > 1 -> removeExtension(inJavaFile[1])
                else -> {
                    null
                }
            }
        }

        classesCoverage.forEach { classCoverage ->
            touchedClasses.forEach { gitFilePath ->
                if (classCoverage.filePath == gitFilePath || classCoverage.filePath.startsWith("$gitFilePath\$")) {
                    addIssue(classCoverage)
                }
            }
        }
    }

    private fun addTotalCoverage(totalCoverage: Int) {
        val level = getLevel(totalCoverage)
        report.issues.add(Issue(level = level, msg = PROJECT_TOTAL_COVERAGE_TITLE.bold(), description = "$totalCoverage%".bold()))
    }

    private fun addIssue(classCoverage: ClassCoverage) {
        val msg = classCoverage.filePath
        val coverage = "${classCoverage.coveredBranches}%"

        val name = classCoverage.fileName.replace('.', '$')

        val filePath = if (htmlFilePath != null) {
            "$htmlFilePath$separator${classCoverage.classPackage}$separator$name.html"
        } else {
            "${classCoverage.classPackage}$separator$name.html"
        }

        val level = getLevel(classCoverage.coveredBranches)
        report.issues.add(Issue(level = level, filePath = filePath, msg = msg, description = coverage))
    }

    private fun getLevel(totalCoverage: Int): Level {
        return when {
            totalCoverage >= classCoverageThreshold -> INFO("✅")
            totalCoverage in 1..classCoverageThreshold -> ERROR("💣")
            else -> ERROR("🔥")
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

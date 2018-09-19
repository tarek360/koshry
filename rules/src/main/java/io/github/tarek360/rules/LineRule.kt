package io.github.tarek360.rules

import io.github.tarek360.gitdiff.GitDiff
import io.github.tarek360.gitdiff.GitFile
import io.github.tarek360.rules.LineRule.LineRuleBuilder
import io.github.tarek360.rules.core.*
import io.github.tarek360.rules.model.File
import io.github.tarek360.rules.model.Line
import io.github.tarek360.rules.core.Level.INFO

class LineRule  constructor(
        var condition: (File, Line) -> Boolean,
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
      file.addedLines.forEach { line ->
        if (condition(File(file), Line(line))) {
          val msg = "Line --> **${line.number}** , File --> **${file.path}**"
          report.issues.add(Issue(msg = msg, level = issueLevel, filePath = file.path, lineNumber = line.number))
        }
      }
    }
  }

  @RuleDsl
  class LineRuleBuilder {
    lateinit var condition: (File, Line) -> Boolean // Required
    var reportTitle = "Line Rule" // Optional
    var issueLevel: Level = INFO // Optional

    fun build(): LineRule = LineRule(condition, reportTitle, issueLevel)
  }
}



fun lineRule(block: LineRuleBuilder.() -> Unit): LineRule = LineRuleBuilder().apply(block).build()

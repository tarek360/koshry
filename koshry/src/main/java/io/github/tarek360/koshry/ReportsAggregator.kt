package io.github.tarek360.koshry

import io.github.tarek360.koshry.url.FileUrlGenerator
import io.github.tarek360.rules.core.Level.ERROR
import io.github.tarek360.rules.core.Level.INFO
import io.github.tarek360.rules.core.Level.WARN
import io.github.tarek360.rules.core.Report

open class ReportsAggregator(private val fileUrlGenerator: FileUrlGenerator) {

  companion object {
    const val KOSHRY_REPORT_TITLE = "## Koshry Report\n\n"
  }

  open fun aggregate(reports: List<Report>): String {

    val commentBuilder = StringBuilder(KOSHRY_REPORT_TITLE)

    if (reports.isEmpty()) {
      commentBuilder
          .append("🎉 Congrats! Every thing is OK!")
      return commentBuilder.toString()
    }

    reports.forEach { report ->
      // Table header
      commentBuilder
          .append('|')
          .append("Status")
          .append('|')
          .append(report.msgTitle)
          .append('|')
          .append(report.descTitle)
          .append('|')
          .append('\n')
          .append("|:-:|-|:-:|")
          .append('\n')

      report.issues.forEach { issue ->

        val filePath = issue.filePath
        val lineNumber = issue.lineNumber
        val message = if (filePath != null) {
          "[${issue.msg}](${fileUrlGenerator.generate(filePath, lineNumber)})"
        } else {
          issue.msg
        }

        val levelText = issue.level.text
        val desc = issue.description

        commentBuilder
            .append('|')
            .append(levelText)
            .append('|')
            .append(message)
            .append('|')
            .append(desc)
            .append('|')
            .append('\n')
      }
      commentBuilder.append('\n')
    }
    return commentBuilder.toString()
  }
}


//https://github.com/tarek360/emptyapp2/pull/2/files#diff-1d37e48f9ceff6d8030570cd36286a61R29
//https://github.com/tarek360/emptyapp2/pull/2/files#diff-1d37e48f9ceff6d8030570cd36286a61R21
//https://github.com/tarek360/emptyapp2/pull/2/files#diff-354f30a63fb0907d4ad57269548329e3R21
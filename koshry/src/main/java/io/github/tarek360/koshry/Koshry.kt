package io.github.tarek360.koshry

import io.github.tarek360.ci.Ci
import io.github.tarek360.core.cl.CommanderImpl
import io.github.tarek360.core.logger
import io.github.tarek360.gitdiff.GitDiff
import io.github.tarek360.githost.Comment
import io.github.tarek360.githost.GitHost
import io.github.tarek360.githost.GitHostInfo
import io.github.tarek360.githost.Status
import io.github.tarek360.githost.Status.Type
import io.github.tarek360.koshry.ReportsAggregator.Companion.KOSHRY_REPORT_TITLE
import io.github.tarek360.koshry.url.FileUrlGenerator
import io.github.tarek360.koshry.url.GithubFileUrlGenerator
import io.github.tarek360.koshry.url.LocalFileUrlGenerator
import io.github.tarek360.koshry.utility.Md5Generator
import io.github.tarek360.rules.report.Issue
import io.github.tarek360.rules.report.Level.ERROR

class Koshry {

  companion object {
    fun run(koshryConfig: KoshryConfig) {

      val ci = CiProvider().provide()

      if (ci != null) {
        logger.i { "Koshry started running on CI.." }
        logger.i { "${ci.javaClass.simpleName} is detected!" }
        runOnCi(ci, koshryConfig)
      } else {
        logger.i { "Koshry started running locally.." }
        runLocally(koshryConfig)
      }
    }

    private fun runOnCi(ci: Ci, koshryConfig: KoshryConfig) {

      val token = ci.gitHostToken
      val pullRequestId = ci.pullRequestId
      val ownerNameRepoName = ci.projectOwnerNameRepoName

      val gitHostInfo = GitHostInfo(
              ownerNameRepoName = ownerNameRepoName,
              pullRequestId = pullRequestId,
              token = token)

      val gitHostProvider = GitHostProvider(gitHostInfo, CommanderImpl())

      val gitHost: GitHost? = gitHostProvider.provide()

      val pullRequest = gitHost?.getPullRequestInfo()

      val baseSha = pullRequest?.baseSha
      val headSha = pullRequest?.headSha

      logger.d { "pullRequestId $pullRequestId" }
      logger.d { "baseSha $baseSha" }
      logger.d { "headSha $headSha" }

      if (baseSha != null && headSha != null) {

        val fileUrlGenerator: FileUrlGenerator = GithubFileUrlGenerator(gitHostInfo, Md5Generator())

        val comment = applyRules(baseSha, headSha, koshryConfig, fileUrlGenerator)
        val commentUrl = gitHost.post(comment)

        val statusType: Type
        val description: String
        if (comment.isFailed) {
          statusType = Type.FAILURE
          description = "Failed, Check the report"
        } else {
          statusType = Type.SUCCESS
          description = "Passed"
        }

        gitHost.post(Status(
                context = "Koshry",
                type = statusType,
                sha = headSha,
                description = description,
                targetUrl = commentUrl
        ))

      } else {
        logger.e { "Something went wrong!" }
        gitHost?.post(Comment("$KOSHRY_REPORT_TITLE Something went wrong!", true))

      }
    }

    private fun runLocally(koshryConfig: KoshryConfig) {

      val fileUrlGenerator: FileUrlGenerator = LocalFileUrlGenerator()

      val baseSha = ""
      val headSha = ""

      val comment = applyRules(baseSha, headSha, koshryConfig, fileUrlGenerator)

      //TODO save local report
    }

    private fun applyRules(headSha: String, baseSha: String, koshryConfig: KoshryConfig,
                           fileUrlGenerator: FileUrlGenerator): Comment {

      var shouldFail = false
      val gitDiff = GitDiff.create(baseSha, headSha)

      val reports = koshryConfig.rules.mapNotNull { rule ->
        val report = rule.apply(gitDiff)
        if (!shouldFail) {
          report?.let {
            shouldFail = hasFailedIssue(report.issues)
          }
        }
        report
      }
      val reportsAggregator = ReportsAggregator(fileUrlGenerator)
      val markDownComment = reportsAggregator.aggregate(reports)
      return Comment(markDownComment, shouldFail)
    }


    private fun hasFailedIssue(issues: MutableList<Issue>): Boolean {
      for (issue in issues) {
        if (issue.level == ERROR) {
          return true
        }
      }
      return false
    }
  }

}

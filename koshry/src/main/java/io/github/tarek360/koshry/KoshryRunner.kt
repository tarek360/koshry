package io.github.tarek360.koshry

import io.github.tarek360.ci.Ci
import io.github.tarek360.core.logger
import io.github.tarek360.githost.Comment
import io.github.tarek360.githost.GitHostInfo
import io.github.tarek360.githost.PullRequest
import io.github.tarek360.koshry.io.FileWriter
import io.github.tarek360.koshry.mapper.CiModelMapper
import io.github.tarek360.koshry.mapper.PullRequestModelMapper
import io.github.tarek360.koshry.url.FileUrlGenerator
import io.github.tarek360.koshry.url.GithubFileUrlGenerator
import io.github.tarek360.koshry.url.LocalFileUrlGenerator
import io.github.tarek360.koshry.utility.Md5Generator

internal class KoshryRunner {

    internal fun runOnCi(ci: Ci, koshryConfig: KoshryConfig, gitHostInfo: GitHostInfo, gitHostController: GitHostController) {

        val pullRequest = gitHostController.getPullRequest()

        val baseSha = pullRequest?.baseSha
        val headSha = pullRequest?.headSha

        if (baseSha == null || headSha == null) {
            logger.e { "Koshry: has stopped!, cant't get the pull request info" }
            return
        }

        koshryConfig.baseSha = baseSha
        koshryConfig.headSha = headSha

        val fileUrlGenerator: FileUrlGenerator = GithubFileUrlGenerator(gitHostInfo, Md5Generator())

        val comment = applyRules(ci, pullRequest, koshryConfig, fileUrlGenerator)

        val commentUrl = gitHostController.postComment(comment)

        gitHostController.postStatus(koshryConfig.headSha, comment.isFailed, commentUrl)
    }

    internal fun runLocally(koshryConfig: KoshryConfig) {

        val fileUrlGenerator: FileUrlGenerator = LocalFileUrlGenerator()

        val comment = applyRules(null, null, koshryConfig, fileUrlGenerator)

        val reportFilePath = "build/reports/koshry.md"
        FileWriter().writeToFile(reportFilePath, comment.msg)
    }

    private fun applyRules(ci: Ci?, pullRequest: PullRequest?, koshryConfig: KoshryConfig, fileUrlGenerator: FileUrlGenerator): Comment {

        val reportsAggregator = ReportsAggregator(fileUrlGenerator)
        val ciMapper = CiModelMapper()
        val pullRequestMapper = PullRequestModelMapper()

        val rulesMan = RulesMan(ciMapper.map(ci), pullRequestMapper.map(pullRequest), reportsAggregator)
        val comment = rulesMan.applyRules(koshryConfig.baseSha, koshryConfig.headSha, koshryConfig.rules)

        println("___________Markdown Comment___________")
        println(comment.msg)
        println("______________________________________")

        if (comment.isFailed) {
            logger.e { "Koshry: One rule or more failed!" }
        } else {
            logger.i { "Koshry: All rules have passed!" }
        }

        return comment
    }
}

package io.github.tarek360.koshry

import io.github.tarek360.githost.Comment
import io.github.tarek360.githost.GitHost
import io.github.tarek360.githost.Status

open class GitHostController(gitHostProvider: GitHostProvider) {

    private var gitHost: GitHost = gitHostProvider.provide()

    open fun getPullRequest()= gitHost.getPullRequestInfo()

    open fun postComment(comment: Comment): String? = gitHost.post(comment)

    open fun postStatus(sha: String, isFailed: Boolean, commentUrl: String?) {

        val statusType: Status.Type
        val description: String
        if (isFailed) {
            statusType = Status.Type.FAILURE
            description = "Failed, Check the report"
        } else {
            statusType = Status.Type.SUCCESS
            description = "Passed"
        }

        val status = Status(
                context = "Koshry",
                type = statusType,
                sha = sha,
                description = description,
                targetUrl = commentUrl
        )

        gitHost.post(status)
    }

}

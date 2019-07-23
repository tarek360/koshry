package io.github.tarek360.githost

import io.github.tarek360.core.logger

class UnknownGitHost : GitHost() {

    init {
        logger.e { "Unknown GitHostType" }
    }

    override fun postComment(comment: Comment): String? {
        logger.w { "Unknown GitHost: Koshry can't post the report" }
        return null
    }

    override fun updateComment(comment: Comment, commentId: Int): String? {
        logger.w { "Unknown GitHost: Koshry can't update the report" }
        return null
    }

    override fun postStatus(status: Status) {
        logger.w { "Unknown GitHost: Koshry can't post the status" }
    }

    override fun pushFile(filePath: String, branchName: String, commitMsg: String) {
        logger.w { "Unknown GitHost: Koshry can't push files" }
    }

    override fun getPullRequestInfo(): PullRequest? {
        logger.w { "Unknown GitHost: Koshry can't get the Pull Request Info" }
        return null
    }

    override fun getPullRequestComments(): List<GitHostComment>? {
        logger.w { "Unknown GitHost: Koshry can't get the Pull Request Info" }
        return null
    }
}

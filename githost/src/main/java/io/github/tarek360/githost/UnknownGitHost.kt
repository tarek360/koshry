package io.github.tarek360.githost

import io.github.tarek360.core.logger

class UnknownGitHost : GitHost {

    override fun post(comment: Comment): String? {
        logger.w { "Unknown GitHost: Koshry can't post the report" }
        return null
    }

    override fun post(status: Status) {
        logger.w { "Unknown GitHost: Koshry can't post the status" }
    }

    override fun pushFile(filePath: String, branchName: String, commitMsg: String) {
        logger.w { "Unknown GitHost: Koshry can't push files" }
    }

    override fun getPullRequestInfo(): PullRequest? {
        logger.w { "Unknown GitHost: Koshry can't get the Pull Request Info" }
        return null
    }
}

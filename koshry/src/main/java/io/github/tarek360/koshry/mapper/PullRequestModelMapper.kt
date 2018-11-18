package io.github.tarek360.koshry.mapper

import io.github.tarek360.rules.core.PullRequest

class PullRequestModelMapper {

    fun map(pullRequest: io.github.tarek360.githost.PullRequest?): PullRequest? {
        return if (pullRequest != null)
            PullRequest(pullRequest.headSha,
                    pullRequest.baseSha,
                    pullRequest.author)
        else
            null
    }
}

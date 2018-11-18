package io.github.tarek360.ci

abstract class Ci {

    open val gitHostToken: String? by lazy {
        val token = Environment.getVariable("KOSHRY_GIT_HOST_TOKEN")
        if (token.isNullOrBlank()) {
            Environment.getVariable("DANGER_GITHUB_API_TOKEN")
        } else {
            token
        }
    }

    abstract val buildId: Int?

    abstract val projectOwnerNameRepoName: String?

    abstract val pullRequestId: Int?
}

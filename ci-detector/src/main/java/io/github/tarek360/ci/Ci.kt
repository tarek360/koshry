package io.github.tarek360.ci

abstract class Ci {

    open val gitHostToken: String? by lazy {
         Environment.getVariable("KOSHRY_GIT_HOST_TOKEN")
    }

    abstract val buildId: Int?

    abstract val projectOwnerNameRepoName: String?

    abstract val pullRequestId: String?
}

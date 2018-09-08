package io.github.tarek360.ci

class CircleCi : Ci() {

    override val buildId: Int? by lazy {
        val buildId: String? = System.getenv("CIRCLE_BUILD_NUM")
        buildId?.toInt()
    }

    override val projectOwnerNameRepoName: String? by lazy {
        val projectUsername: String? = System.getenv("CIRCLE_PROJECT_USERNAME")
        val projectRepoName: String? = System.getenv("CIRCLE_PROJECT_REPONAME")

        if (projectUsername != null && projectRepoName != null) {
            "$projectUsername/$projectRepoName"
        } else {
            null
        }
    }

    override val pullRequestId: Int? by lazy {
        val pullRequestUrl: String? = System.getenv("CIRCLE_PULL_REQUEST")
        val pullRequestId = pullRequestUrl?.substringAfterLast('/')
        pullRequestId?.toInt()
    }


}

package io.github.tarek360.ci

class CircleCi : Ci() {

    override val buildId: Int? by lazy {
        val buildId: String? = Environment.getVariable("CIRCLE_BUILD_NUM")
        buildId?.toInt()
    }

    override val projectOwnerNameRepoName: String? by lazy {
        val projectUsername: String? = Environment.getVariable("CIRCLE_PROJECT_USERNAME")
        val projectRepoName: String? = Environment.getVariable("CIRCLE_PROJECT_REPONAME")

        if (projectUsername != null && projectRepoName != null) {
            "$projectUsername/$projectRepoName"
        } else {
            null
        }
    }

    override val pullRequestId: String? by lazy {
        val pullRequestUrl: String? = Environment.getVariable("CIRCLE_PULL_REQUEST")
        val pullRequestId = pullRequestUrl?.substringAfterLast('/')
        pullRequestId
    }


}

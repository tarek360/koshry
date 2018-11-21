package io.github.tarek360.ci

class TeamCityCi : Ci() {

    override val buildId: Int? by lazy {
        val buildId: String? = Environment.getVariable("TEAMCITY_BUILD_ID")
        buildId?.toInt()
    }

    override val projectOwnerNameRepoName: String? by lazy {
        Environment.getVariable("GITHUB_REPO_SLUG")
    }

    override val pullRequestId: Int?  by lazy {
        val teamcityBuildBranch =
                Environment.getVariable("TEAMCITY_BUILD_BRANCH")?.split("/")
        val id = teamcityBuildBranch?.get(0)
        id?.toInt()
    }
}

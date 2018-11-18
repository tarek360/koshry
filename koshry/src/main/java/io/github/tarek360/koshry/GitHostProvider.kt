package io.github.tarek360.koshry

import io.github.tarek360.githost.GitHost
import io.github.tarek360.githost.GitHostInfo
import io.github.tarek360.githost.UnknownGitHost
import io.github.tarek360.githost.github.GitHub

open class GitHostProvider(private val gitHostInfo: GitHostInfo) {

    open fun provide(): GitHost {

        val domain = gitHostInfo.domain

        val gitHostType: GitHostType = if (domain?.isNotBlank() == true) {
            getGitHostType(domain)
        } else {
            GitHostType.UNKNOWN
        }

        return when (gitHostType) {
            GitHostType.GITHUB -> GitHub(gitHostInfo)
            GitHostType.GITHUB_ENTERPRISE -> GitHub(gitHostInfo, true)
            else -> UnknownGitHost()
        }
    }

    private fun getGitHostType(domain: String): GitHostType {
        domain.run {
            return when {
                this == ("github.com") -> GitHostType.GITHUB
                this.contains("github") -> GitHostType.GITHUB_ENTERPRISE
                this.contains("bitbucket") -> GitHostType.BITBUCKET
                this.contains("gitlab") -> GitHostType.GITLAB
                else -> GitHostType.UNKNOWN
            }
        }
    }

    enum class GitHostType {
        UNKNOWN,
        GITHUB_ENTERPRISE,
        GITHUB,
        BITBUCKET,
        GITLAB
    }

}

package io.github.tarek360.koshry

import io.github.tarek360.core.cl.Commander
import io.github.tarek360.githost.GitHost
import io.github.tarek360.githost.GitHostInfo
import io.github.tarek360.githost.github.GitHub

class GitHostProvider(val gitHostInfo: GitHostInfo, val commander: Commander) {

  fun provide(): GitHost? {

    val output = commander.executeCL("git config --get remote.origin.url")

    val gitHostType: GitHostType = if (output.isNotEmpty()) {
      getGitHostType(output[0])
    } else {
      GitHostType.UNKNOWN
    }

    return when (gitHostType) {
      GitHostType.GITHUB -> GitHub(gitHostInfo)
      else -> null
    }

  }

  private fun getGitHostType(url: String): GitHostType {
    url.run {
      return when {
        this.contains("github") -> GitHostType.GITHUB
        this.contains("bitbucket") -> GitHostType.BITBUCKET
        this.contains("gitlab") -> GitHostType.GITLAB
        else -> GitHostType.UNKNOWN
      }
    }
  }
}
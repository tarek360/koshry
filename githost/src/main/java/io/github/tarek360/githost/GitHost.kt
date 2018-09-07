package io.github.tarek360.githost

interface GitHost {

  /**
   *@return Comment URL
   */
  fun post(comment: Comment): String?

  fun post(status: Status)

  fun pushFile(filePath: String, branchName: String, commitMsg: String)

  fun getPullRequestInfo(): PullRequest?
}

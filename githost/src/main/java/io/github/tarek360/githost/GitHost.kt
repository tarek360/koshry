package io.github.tarek360.githost

interface GitHost {

  /**
   *@return Comment URL
   */
  fun postComment(comment: Comment): String?

  fun updateComment(comment: Comment, commentId: Int): String?

  fun postStatus(status: Status)

  fun pushFile(filePath: String, branchName: String, commitMsg: String)

  fun getPullRequestInfo(): PullRequest?

  fun getPullRequestComments(): List<GitHostComment>?
}

package io.github.tarek360.githost

import io.github.tarek360.core.logger

abstract class GitHost {

  init {
    logger.d { "GitHost name: ${this.javaClass.typeName}" }
  }

  /**
   *@return Comment URL
   */
  abstract fun postComment(comment: Comment): String?

  abstract fun updateComment(comment: Comment, commentId: Int): String?

  abstract fun postStatus(status: Status)

  abstract fun pushFile(filePath: String, branchName: String, commitMsg: String)

  abstract fun getPullRequestInfo(): PullRequest?

  abstract fun getPullRequestComments(): List<GitHostComment>?
}

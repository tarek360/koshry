package io.github.tarek360.ci

abstract class Ci {

  val gitHostToken: String? by lazy {
    System.getenv("KOSHRY_GIT_HOST_TOKEN")
  }

  abstract val buildId: Int?

  abstract val projectOwnerNameRepoName: String?

  abstract val pullRequestId: Int?
}

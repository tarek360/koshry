package io.github.tarek360.ci

class TravisCi : Ci() {

  override val buildId: Int? by lazy {
    val buildId: String? = System.getenv("TRAVIS_BUILD_NUMBER")
    buildId?.toInt()
  }

  override val projectOwnerNameRepoName: String? by lazy {
    System.getenv("TRAVIS_PULL_REQUEST_SLUG")
  }

  override val pullRequestId: Int?  by lazy {
    val buildId: String? = System.getenv("TRAVIS_PULL_REQUEST")
    buildId?.toInt()
  }

}

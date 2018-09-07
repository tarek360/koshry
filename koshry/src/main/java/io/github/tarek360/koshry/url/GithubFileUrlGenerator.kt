package io.github.tarek360.koshry.url

import io.github.tarek360.githost.GitHostInfo
import io.github.tarek360.koshry.utility.Md5Generator

class GithubFileUrlGenerator(
    private val gitHostInfo: GitHostInfo,
    private val md5Generator: Md5Generator
) : FileUrlGenerator {

  override fun generate(filePath: String, lineNumber: Int?): String {

    val line = if (lineNumber != null) {
      "R$lineNumber"
    } else {
      ""
    }

    val ownerNameRepoName = gitHostInfo.ownerNameRepoName
    val pullRequestId = gitHostInfo.pullRequestId
    val filePathMd5 = md5Generator.getMd5(filePath)

    return "https://github.com/$ownerNameRepoName/pull/$pullRequestId/files#diff-$filePathMd5$line"
  }
}

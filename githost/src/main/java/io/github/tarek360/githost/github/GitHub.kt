package io.github.tarek360.githost.github

import io.github.tarek360.core.cl.CommanderImpl
import io.github.tarek360.githost.Comment
import io.github.tarek360.githost.GitHost
import io.github.tarek360.githost.GitHostInfo
import io.github.tarek360.githost.PullRequest
import io.github.tarek360.githost.Status
import io.github.tarek360.githost.network.okhttp
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.net.HttpURLConnection.HTTP_CREATED

var githubApiBaseUrl: String = "https://api.github.com/"

class GitHub(private val gitHostInfo: GitHostInfo) : GitHost {

  private val apiReposUrl: String = "${githubApiBaseUrl}repos/${gitHostInfo.ownerNameRepoName}"
  private val githubCommitCommander = GithubCommitCommander(CommanderImpl(), gitHostInfo)

  override fun post(comment: Comment): String? = postPullRequestComment(comment)

  override fun post(status: Status) {
    postCommitStatus(status)
  }

  override fun pushFile(filePath: String, branchName: String, commitMsg: String) {
    githubCommitCommander.commit(filePath = filePath, branchName = branchName, commitMsg = commitMsg)
  }

  override fun getPullRequestInfo(): PullRequest? {
    val url = "$apiReposUrl/pulls/${gitHostInfo.pullRequestId}"

    val request = Request.Builder()
        .url(url)
        .addHeader("Authorization", "token ${gitHostInfo.token}")
        .get()
        .build()

    val response = okhttp.newCall(request).execute()

    val json = response.body()?.string()

    return GithubPullRequestParser().parse(json)
  }

  private fun postPullRequestComment(comment: Comment): String? {

    val url = "$apiReposUrl/issues/${gitHostInfo.pullRequestId}/comments"

    val bodyJson = JSONObject()
    bodyJson.put("body", comment.msg)

    val body = RequestBody.create(MediaType.parse("application/json"), bodyJson.toString())

    val request = Request.Builder()
        .url(url)
        .addHeader("Authorization", "token ${gitHostInfo.token}")
        .post(body)
        .build()

    val response = okhttp.newCall(request).execute()

    val jsonResponse = JSONObject(response.body()?.string())

    return if (response.code() == HTTP_CREATED) {
      jsonResponse.getString("html_url")
    } else {
      null
    }
  }

  private fun postCommitStatus(status: Status) {

    val url = "$apiReposUrl/statuses/${status.sha}"

    val bodyJson = JSONObject()
    bodyJson.put("context", status.context)
    bodyJson.put("state", status.type.type)
    bodyJson.put("description", status.description)

    status.targetUrl?.run {
      bodyJson.put("target_url", this)
    }

    val body = RequestBody.create(MediaType.parse("application/json"), bodyJson.toString())

    val request = Request.Builder()
        .url(url)
        .addHeader("Authorization", "token ${gitHostInfo.token}")
        .post(body)
        .build()
    okhttp.newCall(request).execute()
  }
}

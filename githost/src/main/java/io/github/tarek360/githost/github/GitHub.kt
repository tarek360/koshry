package io.github.tarek360.githost.github

import io.github.tarek360.core.cl.CommanderImpl
import io.github.tarek360.core.logger
import io.github.tarek360.githost.Comment
import io.github.tarek360.githost.GitHost
import io.github.tarek360.githost.GitHostInfo
import io.github.tarek360.githost.PullRequest
import io.github.tarek360.githost.Status
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.net.HttpURLConnection.HTTP_CREATED

class GitHub(private val gitHostInfo: GitHostInfo) : GitHost {

  private val apiBaseUrl: String = "https://api.github.com/repos/${gitHostInfo.ownerNameRepoName}"
  private val githubCommitCommander = GithubCommitCommander(CommanderImpl(), gitHostInfo)

  override fun post(comment: Comment): String? = postPullRequestComment(comment)

  override fun post(status: Status) {
    postCommitStatus(status)
  }

  override fun pushFile(filePath: String, branchName: String, commitMsg: String) {
    githubCommitCommander.commit(filePath = filePath, branchName = branchName, commitMsg = commitMsg)
  }

  override fun getPullRequestInfo(): PullRequest? {
    val url = "$apiBaseUrl/pulls/${gitHostInfo.pullRequestId}"

    val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { m -> logger.d { m } })

    logger.level = HttpLoggingInterceptor.Level.BODY

    val okhttp = OkHttpClient.Builder().addInterceptor(logger).build()


    val request = Request.Builder()
        .url(url)
        .addHeader("Authorization", "token ${gitHostInfo.token}")
        .get()
        .build()

    val response = okhttp.newCall(request).execute()

    val json = response.body()?.string() ?: ""

    return PullRequestParser().parse(json)
  }

  private fun postPullRequestComment(comment: Comment): String? {

    val url = "$apiBaseUrl/issues/${gitHostInfo.pullRequestId}/comments"

    val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { m -> logger.d { m } })

    logger.level = HttpLoggingInterceptor.Level.BODY

    val okhttp = OkHttpClient.Builder().addInterceptor(logger).build()

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

    val url = "$apiBaseUrl/statuses/${status.sha}"

    val bodyJson = JSONObject()
    bodyJson.put("context", status.context)
    bodyJson.put("state", status.type.type)
    bodyJson.put("description", status.description)

    status.targetUrl?.run {
      bodyJson.put("target_url", this)
    }

    val body = RequestBody.create(MediaType.parse("application/json"), bodyJson.toString())

    val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { m -> println(m) })

    logger.level = HttpLoggingInterceptor.Level.BODY

    val okhttp = OkHttpClient.Builder().addInterceptor(logger).build()

    val request = Request.Builder()
        .url(url)
        .addHeader("Authorization", "token ${gitHostInfo.token}")
        .post(body)
        .build()
    okhttp.newCall(request).execute()
  }
}

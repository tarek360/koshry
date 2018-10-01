package io.github.tarek360.githost.gitlab

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
import java.net.HttpURLConnection

class Gitlab(private val gitHostInfo: GitHostInfo) : GitHost {
    companion object {
        private const val GITLAB_API_BASE_URL = "https://gitlab.com/api/v4/"
    }

    private val apiReposUrl: String =
        "${GITLAB_API_BASE_URL}projects/${gitHostInfo.ownerNameRepoName}"

    override fun post(comment: Comment) = postPullRequestComment(comment)

    override fun post(status: Status) {
        postCommitStatus(status)
    }

    override fun pushFile(filePath: String, branchName: String, commitMsg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPullRequestInfo(): PullRequest? {
        val url =
            "$apiReposUrl/${gitHostInfo.ownerNameRepoName}/merge_requests/${gitHostInfo.pullRequestId}"

        val request = Request.Builder()
            .url(url)
            .addHeader("PRIVATE-TOKEN", "${gitHostInfo.token}")
            .get()
            .build()

        val response = okhttp.newCall(request).execute()

        val json = response.body()?.string()

        return GitlabPullRequestParser().parse(json)
    }

    private fun postPullRequestComment(comment: Comment): String? {
        val url =
            "$apiReposUrl/${gitHostInfo.ownerNameRepoName}/issues/${gitHostInfo.pullRequestId}/notes"

        val bodyJson = JSONObject()
        bodyJson.put("body", comment.msg)

        val body = RequestBody.create(MediaType.parse("application/json"), bodyJson.toString())

        val request = Request.Builder()
            .url(url)
            .addHeader("PRIVATE-TOKEN", "${gitHostInfo.token}")
            .post(body)
            .build()

        val response = okhttp.newCall(request).execute()

        val jsonResponse = JSONObject(response.body()?.string())

        return if (response.code() == HttpURLConnection.HTTP_CREATED) {
            jsonResponse.getString("html_url")
        } else {
            null
        }
    }

    private fun postCommitStatus(status: Status) {
        val url = "$apiReposUrl/${gitHostInfo.ownerNameRepoName}/statuses/${status.sha}"

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
            .addHeader("PRIVATE-TOKEN", "${gitHostInfo.token}")
            .post(body)
            .build()
        okhttp.newCall(request).execute()
    }
}
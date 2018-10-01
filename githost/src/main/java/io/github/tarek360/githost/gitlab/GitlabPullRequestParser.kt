package io.github.tarek360.githost.gitlab

import io.github.tarek360.githost.PullRequest
import org.json.JSONException
import org.json.JSONObject

class GitlabPullRequestParser {

    companion object {
        const val KEY_DIFF_REFS = "diff_refs"
        const val KEY_BASE_SHA = "base_sha"
        const val KEY_HEAD_SHA = "head_sha"
        const val KEY_MERGED_BY = "merged_by"
        const val KEY_USERNAME = "merged_by"
    }

    fun parse(json: String?): PullRequest? {
        var headSha: String? = null
        var baseSha: String? = null
        var author: String? = null

        json?.run {
            try {
                val jsonObject = JSONObject(json)

                if (jsonObject.has(KEY_DIFF_REFS)) {
                    val baseJsonObject = jsonObject.getJSONObject(KEY_DIFF_REFS)
                    if (baseJsonObject.has(KEY_BASE_SHA)) {
                        baseSha = baseJsonObject.getString(KEY_BASE_SHA)
                    }

                    if (baseJsonObject.has(KEY_HEAD_SHA)) {
                        headSha = baseJsonObject.getString(KEY_HEAD_SHA)
                    }
                }

                if (jsonObject.has(KEY_MERGED_BY)) {
                    val baseJsonObject = jsonObject.getJSONObject(KEY_MERGED_BY)
                    if (baseJsonObject.has(KEY_USERNAME)) {
                        author = baseJsonObject.getString(KEY_USERNAME)
                    }
                }

            } catch (ex: JSONException) {
                return null
            }
        }
        return PullRequest(headSha = headSha, baseSha = baseSha, author = author)
    }
}
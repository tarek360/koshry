package io.github.tarek360.githost.github

import io.github.tarek360.githost.PullRequest
import org.json.JSONException
import org.json.JSONObject

class PullRequestParser {

  companion object {
    const val KEY_HEAD = "head"
    const val KEY_BASE = "base"
    const val KEY_SHA = "sha"
  }

  fun parse(json: String?): PullRequest? {
    var headSha: String? = null
    var baseSha: String? = null

    json?.run {
      try {
        val jsonObject = JSONObject(json)

        if (jsonObject.has(KEY_HEAD)) {
          val baseJsonObject = jsonObject.getJSONObject(KEY_HEAD)
          if (baseJsonObject.has(KEY_SHA)) {
            headSha = baseJsonObject.getString(KEY_SHA)
          }
        }

        if (jsonObject.has(KEY_BASE)) {
          val baseJsonObject = jsonObject.getJSONObject(KEY_BASE)
          if (baseJsonObject.has(KEY_SHA)) {
            baseSha = baseJsonObject.getString(KEY_SHA)
          }
        }

      } catch (ex: JSONException) {
        return null
      }
    }
    return PullRequest(headSha = headSha, baseSha = baseSha)
  }

}
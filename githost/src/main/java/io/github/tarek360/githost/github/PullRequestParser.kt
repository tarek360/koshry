package io.github.tarek360.githost.github

import io.github.tarek360.githost.PullRequest
import org.json.JSONException
import org.json.JSONObject

class PullRequestParser {

    companion object {
        const val KEY_HEAD = "head"
        const val KEY_BASE = "base"
        const val KEY_SHA = "sha"
        const val KEY_USER = "user"
        const val KEY_LOGIN = "login"
        const val KEY_TITLE = "title"
        const val KEY_BODY = "body"
        const val KEY_LABELS = "labels"
        const val KEY_LABEL_NAME = "name"
    }

    fun parse(json: String?): PullRequest? {
        var headSha: String? = null
        var baseSha: String? = null
        var title: String? = null
        var body: String? = null
        var author: String? = null
        val labels = arrayListOf<String>()

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

                if (jsonObject.has(KEY_TITLE)) {
                    title = jsonObject.getString(KEY_TITLE)
                }

                if (jsonObject.has(KEY_BODY)) {
                    body = jsonObject.getString(KEY_BODY)
                }

                if (jsonObject.has(KEY_USER)) {
                    val baseJsonObject = jsonObject.getJSONObject(KEY_USER)
                    if (baseJsonObject.has(KEY_LOGIN)) {
                        author = baseJsonObject.getString(KEY_LOGIN)
                    }
                }

                if (jsonObject.has(KEY_LABELS)) {
                    val labelsJsonArray = jsonObject.getJSONArray(KEY_LABELS)
                    labelsJsonArray.forEach {
                        if(it is JSONObject){
                            if (it.has(KEY_LABEL_NAME)) {
                                val name = it.getString(KEY_LABEL_NAME)
                                labels.add(name)
                            }
                        }

                    }
                }


            } catch (ex: JSONException) {
                return null
            }
        }

        return PullRequest(
                headSha = headSha,
                baseSha = baseSha,
                title = title,
                body = body,
                author = author,
                labels = labels
        )
    }

}
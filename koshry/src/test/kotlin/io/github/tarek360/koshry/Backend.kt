package io.github.tarek360.koshry

import io.github.tarek360.githost.github.githubApiBaseUrl
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection.HTTP_OK
import java.net.HttpURLConnection.HTTP_CREATED
import java.net.HttpURLConnection.HTTP_NOT_FOUND

class Backend {

    private val server: MockWebServer = MockWebServer()

    var pullRequestResponseNull = false
    var pullRequestCommentResponseNull = false

    init {
        server.setDispatcher(AssetFileDispatcher())
    }

    fun start() {
        server.start()
        githubApiBaseUrl = server.url("/").toString()
    }

    fun shutdown() {
        server.shutdown()
    }

    private inner class AssetFileDispatcher : Dispatcher() {

        override fun dispatch(request: RecordedRequest): MockResponse {

            if (pullRequestResponseNull) {
                return MockResponse()
                        .setBody("")
                        .setResponseCode(HTTP_OK)
            }

            if (pullRequestCommentResponseNull) {
                return MockResponse()
                        .setBody("{}")
                        .setResponseCode(HTTP_OK)
            }

            println("request.path: ${request.path}")

            var responseCode = HTTP_OK
            val mockResponseFile = when (request.path) {
                "/repos/tarek360/RichPath/pulls/1" -> "github_pull_request_response.json"
                "/repos/tarek360/RichPath/statuses/2739ec582e38c4a8b277acc68bab5b23ffca46a7" ->
                    "github_commit_status_response.json"
                "/repos/tarek360/RichPath/issues/1/comments" -> {
                    responseCode = HTTP_CREATED
                    "github_post_comment_response.json"
                }
                else -> return MockResponse().setResponseCode(HTTP_NOT_FOUND)
            }

            return try {
                val url = Thread.currentThread().contextClassLoader.getResource(mockResponseFile)
                val stream = url.openStream()
                val json = parseStream(stream)
                MockResponse()
                        .setBody(json)
                        .setResponseCode(responseCode)
            } catch (e: IOException) {
                MockResponse()
                        .setResponseCode(HTTP_NOT_FOUND)
            }
        }

        private fun parseStream(stream: InputStream): String {
            val bufferedReader = BufferedReader(InputStreamReader(stream, "UTF-8"))
            return bufferedReader.use(BufferedReader::readText)
        }
    }
}

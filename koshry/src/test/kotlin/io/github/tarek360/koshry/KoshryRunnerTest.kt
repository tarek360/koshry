package io.github.tarek360.koshry

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.github.tarek360.ci.Ci
import io.github.tarek360.githost.GitHostInfo
import io.github.tarek360.githost.PullRequest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class KoshryRunnerTest {


    @Mock
    private lateinit var ci: Ci

    @Mock
    private lateinit var gitHostController: GitHostController


    private val gitHostInfo: GitHostInfo by lazy {
        GitHostInfo("github.com", "tarek360/RichPath", 1, "abcd1234")
    }

    private val koshryConfig by lazy {
        KoshryConfig(baseSha = "", headSha = "")
    }

    private val koshryRunner by lazy {
        KoshryRunner()
    }

    @Test
    fun runLocally() {
        koshryRunner.runLocally(koshryConfig)
    }

    @Test
    fun runOnCi() {
        // Arrange
        val pullRequest = PullRequest("headSha", "cde",
                "title", "body", "tarek360", arrayListOf("bug", "fix"))
        whenever(gitHostController.getPullRequest()).thenReturn(pullRequest)
        whenever(gitHostController.postComment(any())).thenReturn("http://comment.url")

        // Act
        koshryRunner.runOnCi(ci, koshryConfig, gitHostInfo, gitHostController)

        // Assert
        verify(gitHostController).postComment(any())
        verify(gitHostController).postStatus("headSha", false, "http://comment.url")
    }

    @Test
    fun runOnCiWithNullPullRequest() {
        // Arrange
        whenever(gitHostController.getPullRequest()).thenReturn(null)

        // Act
        koshryRunner.runOnCi(ci, koshryConfig, gitHostInfo, gitHostController)

        // Assert
        verify(gitHostController, never()).postComment(any())
        verify(gitHostController, never()).postStatus("headSha", false, "http://comment.url")
    }
}
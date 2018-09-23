package io.github.tarek360.koshry

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.whenever
import io.github.tarek360.core.mustEqual
import io.github.tarek360.core.mustEqualAndNotNull
import io.github.tarek360.githost.Comment
import io.github.tarek360.githost.GitHost
import io.github.tarek360.githost.PullRequest
import io.github.tarek360.githost.Status
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class GitHostControllerTest {


    @Mock
    private lateinit var gitHost: GitHost

    @Mock
    private lateinit var gitHostProvider: GitHostProvider

    private val gitHostController: GitHostController by lazy {
        GitHostController(gitHostProvider)
    }

    @Before
    fun setup() {
        whenever(gitHostProvider.provide()).thenReturn(gitHost)
    }

    @Test
    fun getPullRequestInfo() {
        // Arrange
        val expectedPullRequest = PullRequest("abc", "cde", "tarek360")
        whenever(gitHost.getPullRequestInfo()).thenReturn(expectedPullRequest)


        // Act
        val actualPullRequest = gitHostController.getPullRequestInfo()

        // Assert
        actualPullRequest mustEqualAndNotNull expectedPullRequest

    }

    @Test
    fun postComment() {
        // Arrange
        val expectedUrl = "http://koshry.koshry"
        whenever(gitHost.post(any<Comment>())).thenReturn(expectedUrl)

        // Act
        val actualUrl = gitHostController.postComment(Comment("", false))

        // Assert
        verify(gitHost).post(any<Comment>())
        actualUrl mustEqualAndNotNull expectedUrl

    }

    @Test
    fun postFailedStatus() {
        // Arrange

        // Act
        gitHostController.postStatus("abcdef", true, "http://koshry.koshry")

        // Assert
        val argumentCaptor = argumentCaptor<Status>()
        verify(gitHost).post(argumentCaptor.capture())
        argumentCaptor.firstValue.apply {
            context mustEqual "Koshry"
            type mustEqual Status.Type.FAILURE
            sha mustEqual "abcdef"
            description mustEqual "Failed, Check the report"
            targetUrl mustEqualAndNotNull "http://koshry.koshry"
        }
    }

    @Test
    fun postPassedStatus() {
        // Arrange

        // Act
        gitHostController.postStatus("abcdef", false, "http://koshry.koshry")

        // Assert
        val argumentCaptor = argumentCaptor<Status>()
        verify(gitHost).post(argumentCaptor.capture())
        argumentCaptor.firstValue.apply {
            context mustEqual "Koshry"
            type mustEqual Status.Type.SUCCESS
            sha mustEqual "abcdef"
            description mustEqual "Passed"
            targetUrl mustEqualAndNotNull "http://koshry.koshry"
        }
    }
}

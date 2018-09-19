package io.github.tarek360.koshry

import com.nhaarman.mockitokotlin2.whenever
import io.github.tarek360.ci.Ci
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Before
import org.junit.AfterClass
import org.junit.BeforeClass


@RunWith(MockitoJUnitRunner::class)
class KoshryRunnerTest {

    @Mock
    private lateinit var ci: Ci

    private val koshryConfig by lazy {
        KoshryConfig(baseSha = "", headSha = "")
    }

    private val koshryRunner by lazy {
        KoshryRunner()
    }

    companion object {
        @BeforeClass
        @Throws(Exception::class)
        @JvmStatic
        fun setup() {
            println("setting up")
            Backend().start()
        }

        @AfterClass
        @Throws(Exception::class)
        @JvmStatic
        fun tearDown() {
            Backend().shutdown()
            println("tearing down")
        }
    }

    @Before
    fun prepareTest() {
        Backend().pullRequestResponseNull = false
        Backend().pullRequestCommentResponseNull = false
    }

    @Test
    fun runLocally() {
        koshryRunner.runLocally(koshryConfig)
    }

    @Test
    fun runOnCi() {
        whenever(ci.pullRequestId).thenReturn(1)
        whenever(ci.projectOwnerNameRepoName).thenReturn("tarek360/RichPath")
        whenever(ci.gitHostToken).thenReturn("abcdef123")
        koshryRunner.runOnCi(ci, koshryConfig)
    }

    @Test
    fun runOnCi_nullPullRequestResponse() {
        whenever(ci.pullRequestId).thenReturn(1)
        whenever(ci.projectOwnerNameRepoName).thenReturn("tarek360/RichPath")
        whenever(ci.gitHostToken).thenReturn("abcdef123")

        Backend().pullRequestResponseNull = true

        koshryRunner.runOnCi(ci, koshryConfig)
    }

    @Test
    fun runOnCi_nullPullRequestPostCommentResponse() {
        whenever(ci.pullRequestId).thenReturn(1)
        whenever(ci.projectOwnerNameRepoName).thenReturn("tarek360/RichPath")
        whenever(ci.gitHostToken).thenReturn("abcdef123")

        Backend().pullRequestCommentResponseNull = true

        koshryRunner.runOnCi(ci, koshryConfig)
    }

    @Test
    fun `runOnCi with null pullRequestId`() {
        whenever(ci.pullRequestId).thenReturn(null)
        koshryRunner.runOnCi(ci, koshryConfig)
    }

    @Test
    fun `runOnCi with null projectOwnerNameRepoName`() {
        whenever(ci.pullRequestId).thenReturn(12)
        whenever(ci.projectOwnerNameRepoName).thenReturn(null)
        koshryRunner.runOnCi(ci, koshryConfig)
    }

    @Test
    fun `runOnCi with null gitHostToken`() {
        whenever(ci.pullRequestId).thenReturn(12)
        whenever(ci.projectOwnerNameRepoName).thenReturn("tarek360/RichPath")
        whenever(ci.gitHostToken).thenReturn(null)
        koshryRunner.runOnCi(ci, koshryConfig)
    }

}
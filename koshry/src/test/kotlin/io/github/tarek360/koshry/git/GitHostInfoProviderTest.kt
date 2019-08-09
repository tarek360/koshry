package io.github.tarek360.koshry.git

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.github.tarek360.ci.Ci
import io.github.tarek360.core.cl.Commander
import io.github.tarek360.core.mustEqualAndNotNull
import io.github.tarek360.core.mustNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GitHostInfoProviderTest {

    @Mock
    private lateinit var ci: Ci

    @Mock
    private lateinit var commander: Commander

    @Mock
    private lateinit var gitRemoteUrlParser: GitRemoteUrlParser

    @Test
    fun provide() {
        // Arrange
        val gitHostInfoProvider = GitHostInfoProvider(ci, commander, gitRemoteUrlParser, null)
        val gitRemoteOrigin = GitRemoteOrigin("github.com", "tarek360/koshry")
        whenever(commander.executeCL(any())).thenReturn(listOf("git@github.com:tarek360/koshry.git"))
        whenever(gitRemoteUrlParser.parse(any())).thenReturn(gitRemoteOrigin)
        whenever(ci.pullRequestId).thenReturn("11")
        whenever(ci.gitHostToken).thenReturn("abc123")

        // Act
        val gitHostInfo = gitHostInfoProvider.provide()

        // Assert
        gitHostInfo.domain mustEqualAndNotNull "github.com"
        gitHostInfo.pullRequestId mustEqualAndNotNull "11"
        gitHostInfo.ownerNameRepoName mustEqualAndNotNull "tarek360/koshry"
        gitHostInfo.token mustEqualAndNotNull "abc123"
    }

    @Test
    fun provideWithNullGitRemoteOrigin() {
        // Arrange
        val gitHostInfoProvider = GitHostInfoProvider(ci, commander, gitRemoteUrlParser, null)

        whenever(commander.executeCL(any())).thenReturn(listOf("git@github.com:tarek360/koshry.git"))
        whenever(gitRemoteUrlParser.parse(any())).thenReturn(null)
        whenever(ci.pullRequestId).thenReturn("11")
        whenever(ci.projectOwnerNameRepoName).thenReturn("different than tarek360/koshry")
        whenever(ci.gitHostToken).thenReturn("abc123")

        // Act
        val gitHostInfo = gitHostInfoProvider.provide()

        // Assert
        gitHostInfo.pullRequestId mustEqualAndNotNull "11"
        gitHostInfo.ownerNameRepoName mustEqualAndNotNull "different than tarek360/koshry"
        gitHostInfo.token mustEqualAndNotNull "abc123"
    }

    @Test
    fun provideWithNullValues() {
        // Arrange
        val gitHostInfoProvider = GitHostInfoProvider(ci, commander, gitRemoteUrlParser, null)

        whenever(commander.executeCL(any())).thenReturn(listOf("git@github.com:tarek360/koshry.git"))
        whenever(gitRemoteUrlParser.parse(any())).thenReturn(null)
        whenever(ci.pullRequestId).thenReturn(null)
        whenever(ci.projectOwnerNameRepoName).thenReturn(null)
        whenever(ci.gitHostToken).thenReturn(null)

        // Act
        val gitHostInfo = gitHostInfoProvider.provide()

        // Assert
        gitHostInfo.pullRequestId.mustNull()
        gitHostInfo.ownerNameRepoName.mustNull()
        gitHostInfo.token.mustNull()
    }

}

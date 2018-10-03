package io.github.tarek360.koshry

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.github.tarek360.core.cl.Commander
import io.github.tarek360.core.mustInstanceOf
import io.github.tarek360.githost.GitHostInfo
import io.github.tarek360.githost.UnknownGitHost
import io.github.tarek360.githost.github.GitHub
import io.github.tarek360.githost.gitlab.Gitlab
import org.junit.Test

import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GitHostProviderTest {

    @Mock
    private lateinit var commander: Commander

    private val gitHostProvider: GitHostProvider by lazy {
        GitHostProvider(GitHostInfo("", 0, ""), commander)
    }

    @Test
    fun provideGitHub() {
        // Arrange
        whenever(commander.executeCL(any())).thenReturn(listOf("git@github.com:tarek360/koshry.git"))


        // Act
        val gitHost = gitHostProvider.provide()

        // Assert
        gitHost mustInstanceOf GitHub::class.java
    }

    @Test
    fun provideBitbucket() {
        // Arrange
        whenever(commander.executeCL(any())).thenReturn(listOf("git@bitbucket.org:tarek360/koshry.git"))


        // Act
        val gitHost = gitHostProvider.provide()

        // Assert
        gitHost mustInstanceOf UnknownGitHost::class.java
    }

    @Test
    fun provideGitlab() {
        // Arrange
        whenever(commander.executeCL(any())).thenReturn(listOf("git@gitlab.com:tarek360/koshry.git"))


        // Act
        val gitHost = gitHostProvider.provide()

        // Assert
        gitHost mustInstanceOf Gitlab::class.java
    }

    @Test
    fun provideUnknownGitHost() {
        // Arrange
        whenever(commander.executeCL(any())).thenReturn(listOf("git@unknowgithost.com:tarek360/koshry.git"))


        // Act
        val gitHost = gitHostProvider.provide()

        // Assert
        gitHost mustInstanceOf UnknownGitHost::class.java
    }

    @Test
    fun provideUnknownGitHost_emptyLineReturn() {
        // Arrange
        whenever(commander.executeCL(any())).thenReturn(listOf(""))


        // Act
        val gitHost = gitHostProvider.provide()

        // Assert
        gitHost mustInstanceOf UnknownGitHost::class.java
    }

    @Test
    fun provideUnknownGitHost_emptyListReturn() {
        // Arrange
        whenever(commander.executeCL(any())).thenReturn(emptyList())


        // Act
        val gitHost = gitHostProvider.provide()

        // Assert
        gitHost mustInstanceOf UnknownGitHost::class.java
    }
}

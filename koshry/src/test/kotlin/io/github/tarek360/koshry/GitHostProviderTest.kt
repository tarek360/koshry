package io.github.tarek360.koshry

import io.github.tarek360.core.mustInstanceOf
import io.github.tarek360.githost.GitHostInfo
import io.github.tarek360.githost.UnknownGitHost
import io.github.tarek360.githost.github.GitHub
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GitHostProviderTest {


    @Test
    fun provideGitHub() {
        // Arrange
        val gitHostProvider = GitHostProvider(GitHostInfo("github.com", "", 0, ""))

        // Act
        val gitHost = gitHostProvider.provide()

        // Assert
        gitHost mustInstanceOf GitHub::class.java
    }


    @Test
    fun provideGitHubEnterprise() {
        // Arrange
        val gitHostProvider = GitHostProvider(GitHostInfo("github.company.com", "", 0, ""))

        // Act
        val gitHost = gitHostProvider.provide()

        // Assert
        gitHost mustInstanceOf GitHub::class.java
    }

    @Test
    fun provideBitbucket() {
        // Arrange
        val gitHostProvider = GitHostProvider(GitHostInfo("bitbucket.org", "", 0, ""))


        // Act
        val gitHost = gitHostProvider.provide()

        // Assert
        gitHost mustInstanceOf UnknownGitHost::class.java
    }

    @Test
    fun provideGitlab() {
        // Arrange
        val gitHostProvider = GitHostProvider(GitHostInfo("gitlab.com", "", 0, ""))


        // Act
        val gitHost = gitHostProvider.provide()

        // Assert
        gitHost mustInstanceOf UnknownGitHost::class.java
    }

    @Test
    fun provideUnknownGitHost() {
        // Arrange
        val gitHostProvider = GitHostProvider(GitHostInfo("unknowgithost.com", "", 0, ""))


        // Act
        val gitHost = gitHostProvider.provide()

        // Assert
        gitHost mustInstanceOf UnknownGitHost::class.java
    }

    @Test
    fun provideUnknownGitHost_emptyDomain() {
        // Arrange
        val gitHostProvider = GitHostProvider(GitHostInfo("", "", 0, ""))


        // Act
        val gitHost = gitHostProvider.provide()

        // Assert
        gitHost mustInstanceOf UnknownGitHost::class.java
    }

}

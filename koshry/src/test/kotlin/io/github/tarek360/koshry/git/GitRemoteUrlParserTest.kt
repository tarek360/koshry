package io.github.tarek360.koshry.git

import io.github.tarek360.core.mustEqual
import io.github.tarek360.core.mustNull
import org.junit.Test

class GitRemoteUrlParserTest {

    @Test
    fun parse() {
        // Arrange
        val parser = GitRemoteUrlParser()
        val remoteOriginUrl = "git@github.com:tarek360/koshry.git"

        // Act
        val gitRemoteOrigin = parser.parse(remoteOriginUrl)
                ?: throw AssertionError(" shouldn't return null")

        // Assert
        gitRemoteOrigin.domain mustEqual "github.com"
        gitRemoteOrigin.ownerNameRepoName mustEqual "tarek360/koshry"
    }

    @Test
    fun parseEnterpriseUrl() {
        // Arrange
        val parser = GitRemoteUrlParser()
        val remoteOriginUrl = "git@github.company.org:user/repo.git"

        // Act
        val gitRemoteOrigin = parser.parse(remoteOriginUrl)
                ?: throw AssertionError(" shouldn't return null")

        // Assert
        gitRemoteOrigin.domain mustEqual "github.company.org"
        gitRemoteOrigin.ownerNameRepoName mustEqual "user/repo"
    }

    @Test
    fun parseWrongUrl() {
        // Arrange
        val parser = GitRemoteUrlParser()
        val remoteOriginUrl = "git@github.company.orguser/repo.git"

        // Act
        val gitRemoteOrigin = parser.parse(remoteOriginUrl)

        // Assert

        gitRemoteOrigin.mustNull()
    }
}

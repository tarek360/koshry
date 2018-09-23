package io.github.tarek360.koshry.mapper

import io.github.tarek360.core.mustEqualAndNotNull
import io.github.tarek360.core.mustNotNull
import io.github.tarek360.core.mustNull
import org.junit.Test

class PullRequestModelMapperTest {

    @Test
    fun map() {
        // Arrange
        val pullRequestModelMapper = PullRequestModelMapper()
        val pullRequest = io.github.tarek360.githost.PullRequest(
                "abc", "def", "tarek360")

        // Act
        val result = pullRequestModelMapper.map(pullRequest)

        // Assert
        result.mustNotNull()
        result?.run {
            headSha mustEqualAndNotNull "abc"
            baseSha mustEqualAndNotNull "def"
            author mustEqualAndNotNull "tarek360"
        }
    }

    @Test
    fun mapIfNull() {
        // Arrange
        val pullRequestModelMapper = PullRequestModelMapper()

        // Act
        val result = pullRequestModelMapper.map(null)

        // Assert
        result.mustNull()
    }
}

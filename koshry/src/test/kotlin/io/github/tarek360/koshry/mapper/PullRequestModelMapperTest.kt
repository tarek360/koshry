package io.github.tarek360.koshry.mapper

import io.github.tarek360.core.mustEqualAndNotNull
import io.github.tarek360.core.mustHaveSize
import io.github.tarek360.core.mustNotNull
import io.github.tarek360.core.mustNull
import org.junit.Test

class PullRequestModelMapperTest {

    @Test
    fun map() {
        // Arrange
        val pullRequestModelMapper = PullRequestModelMapper()
        val pullRequest = io.github.tarek360.githost.PullRequest(
                "abc", "def", "title", "body", "tarek360", arrayListOf("bug", "fix"))

        // Act
        val result = pullRequestModelMapper.map(pullRequest)

        // Assert
        result.mustNotNull()
        result?.run {
            headSha mustEqualAndNotNull "abc"
            baseSha mustEqualAndNotNull "def"
            title mustEqualAndNotNull "title"
            body mustEqualAndNotNull "body"
            author mustEqualAndNotNull "tarek360"
            author mustEqualAndNotNull "tarek360"
            labels mustHaveSize 2
            labels[0] mustEqualAndNotNull "bug"
            labels[1] mustEqualAndNotNull "fix"
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

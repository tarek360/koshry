package io.github.tarek360.githost.github

import io.github.tarek360.core.mustEqualAndNotNull
import io.github.tarek360.core.mustNotNull
import io.github.tarek360.core.mustNull
import org.junit.Test

class PullRequestParserTest{

    @Test
    fun parse() {
        // Arrange
        val pullRequestParser = PullRequestParser()

        val json = "{\n" +
                "  \"user\": {\n" +
                "    \"login\": \"tarek360\"\n" +
                "  },\n" +
                "  \"head\": {\n" +
                "    \"sha\": \"abc\"\n" +
                "  },\n" +
                "  \"base\": {\n" +
                "    \"sha\": \"def\"\n" +
                "  }\n" +
                "}"

        // Act
        val result = pullRequestParser.parse(json)

        // Assert
        result.mustNotNull()
        result?.run {
            headSha mustEqualAndNotNull "abc"
            baseSha mustEqualAndNotNull "def"
            author mustEqualAndNotNull "tarek360"
        }
    }

    @Test
    fun parse_null_user_head_base() {

        // Arrange
        val pullRequestParser = PullRequestParser()

        val json = "{}"

        // Act
        val result = pullRequestParser.parse(json)

        // Assert
        result.mustNotNull()
        result?.run {
            headSha.mustNull()
            baseSha.mustNull()
            author.mustNull()
        }
    }

    @Test
    fun parse_null_sha() {
        // Arrange
        val pullRequestParser = PullRequestParser()

        val json = "{\n" +
                "  \"user\": {\n" +
                "  },\n" +
                "  \"head\": {\n" +
                "  },\n" +
                "  \"base\": {\n" +
                "  }\n" +
                "}"

        // Act
        val result = pullRequestParser.parse(json)

        // Assert
        result.mustNotNull()
        result?.run {
            headSha.mustNull()
            baseSha.mustNull()
            author.mustNull()
        }
    }
}

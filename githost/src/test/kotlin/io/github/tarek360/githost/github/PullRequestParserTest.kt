package io.github.tarek360.githost.github

import io.github.tarek360.core.*
import org.junit.Test

class PullRequestParserTest {

    @Test
    fun parse() {
        // Arrange
        val pullRequestParser = PullRequestParser()

        val json = "{\n" +
                "\"title\": \"pr title\"," +
                "\"body\": \"pr description\","+
                "  \"user\": {\n" +
                "    \"login\": \"tarek360\"\n" +
                "  },\n" +
                "  \"head\": {\n" +
                "    \"sha\": \"abc\"\n" +
                "  },\n" +
                "  \"base\": {\n" +
                "    \"sha\": \"def\"\n" +
                "  },\n" +
                "\"labels\": [\n" +
                "  {\n" +
                "    \"name\": \"bug\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"fix\"\n" +
                "  }\n" +
                "],"+
                "}"

        // Act
        val result = pullRequestParser.parse(json)

        // Assert
        result.mustNotNull()
        result?.run {
            headSha mustEqualAndNotNull "abc"
            baseSha mustEqualAndNotNull "def"
            author mustEqualAndNotNull "tarek360"
            title mustEqualAndNotNull "pr title"
            body mustEqualAndNotNull "pr description"
            labels mustHaveSize 2
            labels[0] mustEqualAndNotNull "bug"
            labels[1] mustEqualAndNotNull "fix"
        }
    }

    @Test
    fun parse_null() {

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
            title.mustNull()
            body.mustNull()
            labels.mustEmpty()
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

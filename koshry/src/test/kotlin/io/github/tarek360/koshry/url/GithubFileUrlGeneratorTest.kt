package io.github.tarek360.koshry.url

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.github.tarek360.core.mustEqual
import io.github.tarek360.githost.GitHostInfo
import io.github.tarek360.koshry.utility.Md5Generator
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GithubFileUrlGeneratorTest {

    private lateinit var githubFileUrlGenerator: GithubFileUrlGenerator

    @Mock
    private lateinit var md5Generator: Md5Generator

    @Before
    fun setup() {
        val gitHostInfo = GitHostInfo("github.com", "tarek360/koshry", "77", "")
        githubFileUrlGenerator = GithubFileUrlGenerator(gitHostInfo, md5Generator)
    }

    @Test
    fun test() {
        // Arrange
        val filePath = "any path"
        whenever(md5Generator.getMd5(filePath)).thenReturn("abc123")

        // Act
        val url = githubFileUrlGenerator.generate(filePath, null)


        // Assert

        url mustEqual "https://github.com/tarek360/koshry/pull/77/files#diff-abc123"
    }

    @Test
    fun testWithLineNumber() {
        // Arrange
        val filePath = "any path"
        whenever(md5Generator.getMd5(filePath)).thenReturn("abc123")

        // Act
        val url = githubFileUrlGenerator.generate("any path", 18)


        // Assert

        url mustEqual "https://github.com/tarek360/koshry/pull/77/files#diff-abc123R18"
    }
}

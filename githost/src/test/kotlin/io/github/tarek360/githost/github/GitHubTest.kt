package io.github.tarek360.githost.github

import io.github.tarek360.githost.Comment
import io.github.tarek360.githost.GitHostInfo
import io.github.tarek360.githost.Status
import org.junit.Test

class GitHubTest {

    @Test
    fun postComment() {
        val gitHostInfo = GitHostInfo("tarek360/RichPath", 1, "abcd1234")
        val gitHub = GitHub(gitHostInfo)
        gitHub.post(Comment("Hi", false))
    }

    @Test
    fun postStatus() {
        val gitHostInfo = GitHostInfo("tarek360/RichPath", 1, "abcd1234")
        val gitHub = GitHub(gitHostInfo)
        gitHub.post(Status("", Status.Type.SUCCESS, "abcef98765", "Status description", null))
    }
}

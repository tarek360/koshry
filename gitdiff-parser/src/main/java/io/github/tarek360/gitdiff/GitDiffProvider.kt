package io.github.tarek360.gitdiff

import io.github.tarek360.gitdiffprovider.GitCommanderProvider

class GitDiffProvider {
    companion object {
        fun provide(baseSha: String, headSha: String): GitDiff {
            val gitCommander = GitCommanderProvider.provide(baseSha, headSha)
            return GitDiffImpl(gitCommander)
        }
    }
}

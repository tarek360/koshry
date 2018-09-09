package io.github.tarek360.gitdiff

import io.github.tarek360.gitdiffprovider.GitCommanderProvider

interface GitDiff {

    companion object {
        fun create(baseSha: String, headSha: String): GitDiff {
            val gitCommander = GitCommanderProvider.provide(baseSha, headSha)
            return GitDiffImpl(gitCommander)
        }
    }

    fun getAddedFiles(): List<GitFile>

    fun getDeletedFiles(): List<GitFile>

    fun getModifiedFiles(): List<GitFile>
}

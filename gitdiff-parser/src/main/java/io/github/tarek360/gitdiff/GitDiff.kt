package io.github.tarek360.gitdiff

interface GitDiff {

    fun getAddedFiles(): List<GitFile>

    fun getDeletedFiles(): List<GitFile>

    fun getModifiedFiles(): List<GitFile>
}

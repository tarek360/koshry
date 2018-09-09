package io.github.tarek360.rules.test

import io.github.tarek360.gitdiff.GitDiff
import io.github.tarek360.gitdiff.GitFile

class TestGitDiff(private val addedFiles: List<GitFile>,
                  private val deletedFiles: List<GitFile>,
                  private val modifiedFiles: List<GitFile>
) : GitDiff {

    override fun getAddedFiles(): List<GitFile> = addedFiles

    override fun getDeletedFiles(): List<GitFile> = deletedFiles

    override fun getModifiedFiles(): List<GitFile> = modifiedFiles
}

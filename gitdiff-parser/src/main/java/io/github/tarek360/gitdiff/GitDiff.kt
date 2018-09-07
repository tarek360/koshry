package io.github.tarek360.gitdiff

import io.github.tarek360.gitdiff.model.GitFile
import io.github.tarek360.gitdiffprovider.GitCommander
import io.github.tarek360.gitdiffprovider.GitCommanderProvider
import io.github.tarek360.gitdiffprovider.command.model.Command
import io.github.tarek360.gitdiffprovider.dsl.command
import io.github.tarek360.gitdiffprovider.model.GitDiffCommand
import io.github.tarek360.gitdiffprovider.model.GitDiffCommand.Option.DIFF_FILTER_ADDED
import io.github.tarek360.gitdiffprovider.model.GitDiffCommand.Option.DIFF_FILTER_DELETED
import io.github.tarek360.gitdiffprovider.model.GitDiffCommand.Option.DIFF_FILTER_MODIFIED
import io.github.tarek360.gitdiffprovider.model.GitDiffCommand.Option.NAME_ONLY

class GitDiff private constructor(private val gitCommander: GitCommander) {

  companion object {
    fun create(baseSha: String, headSha: String): GitDiff {
      val gitCommander = GitCommanderProvider.provide(baseSha, headSha)
      return GitDiff(gitCommander)
    }
  }

  fun getAddedFiles(): List<GitFile> = executeAndGitFiles(
      buildDiffCommandFilesNameOnly().apply {
        options.add(DIFF_FILTER_ADDED.toString())
      })

  fun getDeletedFiles(): List<GitFile> = executeAndGitFiles(
      buildDiffCommandFilesNameOnly().apply {
        options.add(DIFF_FILTER_DELETED.toString())
      })

  fun getModifiedFiles(): List<GitFile> = executeAndGitFiles(
      buildDiffCommandFilesNameOnly().apply {
        options.add(DIFF_FILTER_MODIFIED.toString())
      })

  private fun buildDiffCommandFilesNameOnly(): Command {
    return command {
      base = GitDiffCommand.Base.DIFF
      options {
        option = NAME_ONLY.toString()
      }
    }
  }

  private fun executeAndGitFiles(command: Command): List<GitFile> = gitCommander.execute(command).map {
    GitFile(it, gitCommander)
  }

}

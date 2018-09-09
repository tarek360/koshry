package io.github.tarek360.gitdiff

import io.github.tarek360.gitdiffprovider.GitCommander
import io.github.tarek360.gitdiffprovider.command.model.Command
import io.github.tarek360.gitdiffprovider.dsl.command
import io.github.tarek360.gitdiffprovider.model.GitDiffCommand
import io.github.tarek360.gitdiffprovider.model.GitDiffCommand.Option.DIFF_FILTER_ADDED
import io.github.tarek360.gitdiffprovider.model.GitDiffCommand.Option.DIFF_FILTER_DELETED
import io.github.tarek360.gitdiffprovider.model.GitDiffCommand.Option.DIFF_FILTER_MODIFIED
import io.github.tarek360.gitdiffprovider.model.GitDiffCommand.Option.NAME_ONLY

internal class GitDiffImpl internal constructor(private val gitCommander: GitCommander) : GitDiff {

    override fun getAddedFiles(): List<GitFileImpl> = executeAndGitFiles(
            GitFile.Type.ADDED,
            buildDiffCommandFilesNameOnly().apply {
                options.add(DIFF_FILTER_ADDED.toString())
            })

    override fun getDeletedFiles(): List<GitFileImpl> = executeAndGitFiles(
            GitFile.Type.DELETED,
            buildDiffCommandFilesNameOnly().apply {
                options.add(DIFF_FILTER_DELETED.toString())
            })

    override fun getModifiedFiles(): List<GitFileImpl> = executeAndGitFiles(
            GitFile.Type.MODIFIED,
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

    private fun executeAndGitFiles(type: GitFile.Type, command: Command): List<GitFileImpl> = gitCommander.execute(command).map {
        GitFileImpl(it, type, gitCommander)
    }

}

package io.github.tarek360.gitdiff.model

import io.github.tarek360.gitdiffprovider.GitCommander
import io.github.tarek360.gitdiffprovider.command.model.Command
import io.github.tarek360.gitdiffprovider.dsl.command
import io.github.tarek360.gitdiffprovider.model.GitDiffCommand.Base.DIFF
import io.github.tarek360.gitdiffprovider.model.GitDiffCommand.Option.UNIFIED_0

class GitFile(val path: String, private val gitCommander: GitCommander) {

  val addedLines by lazy {
    lines.addedLines
  }

  val deletedLines by lazy {
    lines.deletedLines
  }

  private val lines: Lines by lazy {
    Parser().parse(getDiff())
  }

  private val diffCommand: Command by lazy {
    command {
      base = DIFF
      options {
        option = UNIFIED_0.toString()
      }
      end = "-- $path"
    }
  }

  private fun getDiff(): List<String> = gitCommander.execute(diffCommand)
}

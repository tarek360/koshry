package io.github.tarek360.gitdiffprovider

import io.github.tarek360.core.cl.Commander
import io.github.tarek360.gitdiffprovider.command.model.Command

class GitDiffCommander(
    private val commander: Commander,
    private val sha1: String,
    private val sha2: String)
  : GitCommander {

  override fun execute(command: Command): List<String> {

    command.params.add(sha1)
    command.params.add(sha2)

    return commander.executeCL(command.toString())
  }
}

package io.github.tarek360.gitdiffprovider

import io.github.tarek360.gitdiffprovider.command.model.Command

interface GitCommander {

  fun execute(command: Command): List<String>
}

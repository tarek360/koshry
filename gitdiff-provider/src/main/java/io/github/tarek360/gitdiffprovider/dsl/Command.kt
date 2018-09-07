package io.github.tarek360.gitdiffprovider.dsl

import io.github.tarek360.gitdiffprovider.command.model.Command
import io.github.tarek360.gitdiffprovider.model.GitDiffCommand.Base


fun command(block: CommandBuilder.() -> Unit): Command = CommandBuilder().apply(block).build()

class CommandBuilder {

  lateinit var base: Base
  var end = ""
  private val params = ArrayList<String>()
  private val options = ArrayList<String>()

  fun params(block: PARAMS.() -> Unit) {
    params.addAll(PARAMS().apply(block).build())
  }

  fun options(block: OPTIONS.() -> Unit) {
    options.addAll(OPTIONS().apply(block).build())
  }

  fun build(): Command = Command(base.toString(), params, options, end)

}

class PARAMS {
  private val params = mutableListOf<String>()

  var param: String = ""
    set(value) {
      params.add(value)
    }

  fun build(): List<String> = params
}

class OPTIONS {
  private val options = mutableListOf<String>()

  var option: String = ""
    set(value) {
      options.add(value)
    }

  fun build(): List<String> = options
}

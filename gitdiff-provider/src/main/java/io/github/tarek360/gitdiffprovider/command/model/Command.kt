package io.github.tarek360.gitdiffprovider.command.model

data class Command(val base: String,
    val params: ArrayList<String> = ArrayList(),
    val options: ArrayList<String> = ArrayList(),
    val end: String = "") {

  override fun toString(): String {
    val command = StringBuilder(base)
    params.forEach {
      command.append(" $it")
    }

    options.forEach {
      command.append(" $it")
    }
    command.append(" $end")
    return command.toString()
  }
}

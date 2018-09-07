package io.github.tarek360.gitdiffprovider.model

class GitDiffCommand {
  enum class Base(command: String) {
    STATUS("status"),
    DIFF("diff");

    var base: String = "git $command"
    override fun toString(): String {
      return base
    }
  }

  enum class Option(val opt: String = "") {
    NAME_ONLY("--name-only"),
    DIFF_FILTER_ADDED("--diff-filter=A"),
    DIFF_FILTER_MODIFIED("--diff-filter=M"),
    DIFF_FILTER_DELETED("--diff-filter=D"),
    UNIFIED_0("-U0");

    override fun toString(): String {
      return opt
    }
  }
}

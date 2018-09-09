package io.github.tarek360.gitdiff

interface GitFile {

  enum class Type {
    ADDED,
    MODIFIED,
    DELETED
  }

  val type: Type

  val path: String

  val addedLines : List<Line>

  val deletedLines : List<Line>
}

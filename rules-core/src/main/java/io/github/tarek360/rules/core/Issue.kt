package io.github.tarek360.rules.core

data class Issue(val msg: String, val level: Level, val filePath: String? = null, val lineNumber: Int? = null, val description: String = "")

enum class Level {
  INFO,
  WARN,
  ERROR
}

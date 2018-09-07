package io.github.tarek360.rules.report

data class Issue(val msg: String, val level: Level, val filePath: String? = null, val lineNumber: Int? = null)

enum class Level {
  INFO,
  WARN,
  ERROR
}

package io.github.tarek360.rules.core

data class Issue(val msg: String, val level: Level, val filePath: String? = null, val lineNumber: Int? = null, val description: String = "")

sealed class Level(open var text: String) {
    data class ERROR(override var text: String = "❌") : Level(text)
    data class WARN(override var text: String = "⚠️") : Level(text)
    data class INFO(override var text: String = "ℹ️") : Level(text)
}

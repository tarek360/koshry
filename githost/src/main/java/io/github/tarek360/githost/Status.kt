package io.github.tarek360.githost

data class Status(
    val context: String,
    val type: Type,
    val sha: String,
    val description: String,
    val targetUrl: String? = null
) {

  enum class Type(val type: String) {
    SUCCESS("success"),
    FAILURE("failure"),
    PENDING("pending")
  }
}

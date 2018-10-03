package io.github.tarek360.rules.core

data class Ci(
    val gitHostToken: String?,
    val buildId: Int?,
    val projectId: String?,
    val pullRequestId: Int?
)
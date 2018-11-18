package io.github.tarek360.rules.core

data class Ci(
        val gitHostToken: String?,
        val buildId: Int?,
        val projectOwnerNameRepoName: String?,
        val pullRequestId: Int?)


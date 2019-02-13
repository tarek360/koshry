package io.github.tarek360.rules.core

data class PullRequest constructor(val headSha: String?,
                                   val baseSha: String?,
                                   val title: String?,
                                   val body: String?,
                                   val author: String?,
                                   val labels: ArrayList<String>
)

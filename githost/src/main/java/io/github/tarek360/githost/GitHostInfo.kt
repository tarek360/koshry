package io.github.tarek360.githost

/**
 * git Host "ProjectID"
 * For Github -> should be constructed from ${ownerNameRepoName}. example tarek360/RichPath
 * For gitlab -> should be the project ID . example "4910441"
 */
data class GitHostInfo(val projectId: String?, val pullRequestId: Int?, val token: String?)
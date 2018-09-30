package io.github.tarek360.koshry.git

open class GitRemoteUrlParser {

    open fun parse(remoteOriginUrl: String): GitRemoteOrigin? {

        val url = remoteOriginUrl.split(':')

        if (url.size == 2) {
            val domain = url[0].removePrefix("git@")
            val ownerNameRepoName = url[1].removeSuffix(".git")
            return GitRemoteOrigin(domain, ownerNameRepoName)

        }
        return null
    }
}

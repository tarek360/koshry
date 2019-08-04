package io.github.tarek360.koshry.git

import io.github.tarek360.core.logger

open class GitRemoteUrlParser {

    open fun parse(remoteOriginUrl: String): GitRemoteOrigin? {

        try {
            val url = remoteOriginUrl.split(':')

            if (url.size == 2) {
                val domain = url[0].removePrefix("git@")
                val ownerNameRepoName = url[1].removeSuffix(".git")
                return GitRemoteOrigin(domain, ownerNameRepoName)
            }

        } catch (e: Exception) {
            logger.e { "Can't parse: $remoteOriginUrl" }
        }
        return null
    }
}

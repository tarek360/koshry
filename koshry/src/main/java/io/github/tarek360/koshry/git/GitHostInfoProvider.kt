package io.github.tarek360.koshry.git

import io.github.tarek360.ci.Ci
import io.github.tarek360.core.cl.Commander
import io.github.tarek360.core.logger
import io.github.tarek360.githost.GitHostInfo

open class GitHostInfoProvider constructor(private val ci: Ci,
                                           private val commander: Commander,
                                           private val gitRemoteUrlParser: GitRemoteUrlParser) {

    companion object {
        const val GIT_REMOTE_ORIGIN_URL_COMMAND = "git config --get remote.origin.url"
    }

    open fun provide(): GitHostInfo {

        try {

            val remoteOriginUrl = commander.executeCL(GIT_REMOTE_ORIGIN_URL_COMMAND)[0]
            val gitRemoteOrigin = gitRemoteUrlParser.parse(remoteOriginUrl)

            val pullRequestId = ci.pullRequestId
            val ownerNameRepoName = gitRemoteOrigin?.ownerNameRepoName ?: ci.projectOwnerNameRepoName
            val token = ci.gitHostToken

            if (pullRequestId == null) {
                logger.e { "Can't find Pull Request ID in environment variables." }
            }
            if (ownerNameRepoName == null) {
                logger.e { "Can't find owner name and/or repo name in environment variables." }
            }
            if (token == null) {
                logger.e { "Can't find 'KOSHRY_GIT_HOST_TOKEN' in environment variables." }
            }

            logger.d { "pullRequestId $pullRequestId, ownerNameRepoName $ownerNameRepoName, token $token" }

            return GitHostInfo(
                    domain = gitRemoteOrigin?.domain,
                    ownerNameRepoName = ownerNameRepoName,
                    pullRequestId = pullRequestId,
                    token = token)

        } catch (e: Exception) {
            logger.e { "Something went wrong! Can't provide GitHostInfo!" }
            e.printStackTrace()
            throw e
        }
    }

}

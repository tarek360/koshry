package io.github.tarek360.koshry.mapper

import io.github.tarek360.rules.core.Ci

class CiModelMapper {

    fun map(ci: io.github.tarek360.ci.Ci?): Ci? {
        return if (ci != null)
            Ci(ci.gitHostToken,
                    ci.buildId,
                    ci.projectOwnerNameRepoName,
                    ci.pullRequestId)
        else
            null
    }
}

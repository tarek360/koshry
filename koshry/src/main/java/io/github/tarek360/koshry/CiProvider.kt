package io.github.tarek360.koshry

import io.github.tarek360.ci.Ci
import io.github.tarek360.ci.CircleCi
import io.github.tarek360.ci.Environment
import io.github.tarek360.ci.TeamCityCi
import io.github.tarek360.ci.TravisCi

class CiProvider {

    fun provide(): Ci? {
        return when {
            Environment.getVariable("TRAVIS") == "true" -> TravisCi()
            Environment.getVariable("CIRCLECI") == "true" -> CircleCi()
            Environment.getVariable("TEAMCITY_BUILD_ID")?.isNotBlank() == true -> TeamCityCi()
            else -> null
        }
    }
}

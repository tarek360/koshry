package io.github.tarek360.koshry

import io.github.tarek360.ci.Ci
import io.github.tarek360.core.cl.CommanderImpl
import io.github.tarek360.core.logger
import io.github.tarek360.koshry.git.GitHostInfoProvider
import io.github.tarek360.koshry.git.GitRemoteUrlParser

class Koshry {
    companion object {
        fun run(koshryConfig: KoshryConfig) {
            val ci: Ci? = CiProvider().provide()
            val runner = KoshryRunner()
            if (ci != null) {
                logger.i { "${ci.javaClass.simpleName} is detected!" }
                logger.i { "Koshry has started to run on ${ci.javaClass.simpleName}.." }

                val gitHostInfo = GitHostInfoProvider(ci, CommanderImpl(), GitRemoteUrlParser()).provide()

                val gitHostProvider = GitHostProvider(gitHostInfo)

                val gitHostController = GitHostController(gitHostProvider)

                runner.runOnCi(ci, koshryConfig, gitHostInfo, gitHostController)
            } else {
                logger.i { "Koshry has started to run locally.." }
                runner.runLocally(koshryConfig)
            }
        }
    }
}

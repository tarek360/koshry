package io.github.tarek360.koshry

import io.github.tarek360.ci.Ci
import io.github.tarek360.core.logger

class Koshry {
    companion object {
        fun run(koshryConfig: KoshryConfig) {
            val ci: Ci? = CiProvider().provide()
            val runner = KoshryRunner()
            if (ci != null) {
                logger.i { "${ci.javaClass.simpleName} is detected!" }
                logger.i { "Koshry started running on ${ci.javaClass.simpleName}.." }
                runner.runOnCi(ci, koshryConfig)
            } else {
                logger.i { "Koshry started running locally.." }
                runner.runLocally(koshryConfig)
            }
        }
    }
}

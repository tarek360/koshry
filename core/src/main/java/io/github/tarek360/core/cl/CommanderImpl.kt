package io.github.tarek360.core.cl

import io.github.tarek360.core.logger
import java.io.BufferedReader

class CommanderImpl : Commander {

    override fun executeCL(command: String): List<String> {
        return execute(command)
    }

    override fun executeSh(filePath: String) {

    }

    private fun execute(command: String): List<String> {
        logger.d { "COMMAND EXECUTED: {$command}" }
        val proc = Runtime.getRuntime().exec(command)
        val lines = proc.inputStream.bufferedReader().use(BufferedReader::readLines)
        proc.waitFor()
        if (lines.isEmpty()) {
            logger.e { "COMMAND {$command} HAS NO OUTPUT!" }
        } else {
            logger.d { "COMMAND OUTPUT LINES:" }
            lines.forEach { logger.d { "- $it" } }
            logger.d { "------------- OUTPUT END!\n" }
        }
        return lines
    }
}

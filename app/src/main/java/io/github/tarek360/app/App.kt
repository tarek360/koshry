package io.github.tarek360.app

import io.github.tarek360.koshry.Koshry
import io.github.tarek360.koshry.koshry
import io.github.tarek360.rules.lineRule
import io.github.tarek360.rules.protectedFileRule
import io.github.tarek360.rules.report.Level.ERROR
import io.github.tarek360.rules.report.Level.WARN

fun main(_args: Array<String>) {

    val configuration = koshry {

        rules {
            rule = protectedFileRule {
                reportTitle = "Files are protected and can't be modified, ask @tarek360 to modify"
                issueLevel = ERROR
                files {
                    filePath = ".circleci/config.yml"
                    filePath = ".travis.yml"
                }
            }

            rule = lineRule {
                condition = { line -> line.contains("detected") }
                reportTitle = "I detected a 'detected' word 🤩"
                issueLevel = WARN
            }
        }
    }

    Koshry.run(configuration)
}

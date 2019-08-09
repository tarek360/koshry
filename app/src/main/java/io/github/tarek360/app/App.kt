package io.github.tarek360.app

import io.github.tarek360.koshry.Koshry
import io.github.tarek360.koshry.koshry
import io.github.tarek360.rules.coverage.jacocoCoverageRule
import io.github.tarek360.rules.lineRule
import io.github.tarek360.rules.fileRule
import io.github.tarek360.rules.protectedFileRule
import io.github.tarek360.rules.core.Level.ERROR

fun main(_args: Array<String>) {

    val configuration = koshry {
        debuggable = true

        rules {
            rule = protectedFileRule {
                reportTitle = "Files are protected and can't be modified, ask @tarek360 to modify"
                issueLevel = ERROR()
                files {
                    filePath = ".circleci/config.yml"
                    filePath = ".travis.yml"
                }
                excludeAuthors {
                    author = "tarek360"
                }
            }

            rule = lineRule {
                condition = { file , line -> line.text.contains("System.getenv") }
                reportTitle = "Don't use System.getenv directly, use Environment.getVariable instead."
                issueLevel = ERROR()
            }

            // Rule to prevent anyone from adding new java code.
            rule = fileRule {
                condition = { file ->
                    file.isAdded && file.name.endsWith(".java")
                }
                reportTitle = "Don't add new Java files, use Kotlin instead."
                issueLevel = ERROR()
            }

            // JaCoCo Test Coverage Rule
            rule = jacocoCoverageRule {
                classCoverageThreshold = 79 //79%
                csvFilePath = "build/reports/jacoco/jacoco.csv"
            }
        }
    }

//    Koshry.run(configuration)
    Koshry.run(configuration, listOf("27", "28"))
}

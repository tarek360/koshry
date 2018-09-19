package io.github.tarek360.koshry

import io.github.tarek360.rules.core.Level
import io.github.tarek360.rules.fileRule
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test

class KoshryTest {

    companion object {
        @BeforeClass
        @Throws(Exception::class)
        @JvmStatic
        fun setup() {
            println("setting up")
            Backend().start()
        }

        @AfterClass
        @Throws(Exception::class)
        @JvmStatic
        fun tearDown() {
            Backend().shutdown()
            println("tearing down")
        }
    }

    @Test
    fun runLocal() {

        val configuration = koshry {

            rules {
                // Rule to prevent anyone from adding new java code.
                rule = fileRule {
                    condition = { file ->
                        file.isAdded && file.name.endsWith(".java")
                    }
                    reportTitle = "Don't add new Java files, use Kotlin instead."
                    issueLevel = Level.ERROR
                }
            }
        }

        Koshry.run(configuration)
    }

    @Test
    fun runCi() {

        val configuration = koshry {

            rules {
                // Rule to prevent anyone from adding new java code.
                rule = fileRule {
                    condition = { file ->
                        file.isAdded && file.name.endsWith(".java")
                    }
                    reportTitle = "Don't add new Java files, use Kotlin instead."
                    issueLevel = Level.ERROR
                }
            }
        }

        Koshry.run(configuration)
    }
}
package io.github.tarek360.rules.coverage

import io.github.tarek360.rules.core.Issue
import io.github.tarek360.rules.core.Level
import io.github.tarek360.rules.test.testRule
import org.junit.Before
import org.junit.Test
import java.io.File.separator

class JacocoCoverageRuleTest {

    private lateinit var csvParser: CsvParser

    @Before
    fun setup() {

        csvParser = object : CsvParser {
            override fun parse(csvFilePath: String): ArrayList<ClassCoverage> {
                return arrayListOf(
                        ClassCoverage("com.android.game", "App",
                                "com/android/game/App", 95),
                        ClassCoverage("com.android.game", "Activity",
                                "com/android/game/Activity", 80),
                        ClassCoverage("com.android.game", "Presenter",
                                "com/android/game/Presenter", 50),
                        ClassCoverage("com.android.game", "Presenter.InnerClass",
                                "com/android/game/Presenter\$InnerClass", 30)
                )
            }
        }
    }

    @Test
    fun test() {

        // Arrange

        val htmlFilePath = "https://tarek360.github.io/koshry/build/reports"


        val rule = JacocoCoverageRule(
                classCoverageThreshold = 80,
                csvFilePath = "build/reports/jacoco/jacoco.csv",
                htmlFilePath = htmlFilePath,
                csvParser = csvParser
        )


        val expectedIssue1 = Issue(
                msg = "**com/android/game/App**",
                level = Level.INFO,
                filePath = "$htmlFilePath${separator}com.android.game${separator}App.html",
                description = "🏆 **95%**")


        val expectedIssue2 = Issue(
                msg = "**com/android/game/Activity**",
                level = Level.INFO,
                filePath = "$htmlFilePath${separator}com.android.game${separator}Activity.html",
                description = "✅ **80%**")


        val expectedIssue3 = Issue(
                msg = "**com/android/game/Presenter**",
                level = Level.ERROR,
                filePath = "$htmlFilePath${separator}com.android.game${separator}Presenter.html",
                description = "💣 **50%**")

        val expectedIssue4 = Issue(
                msg = "**com/android/game/Presenter\$InnerClass**",
                level = Level.ERROR,
                filePath = "$htmlFilePath${separator}com.android.game${separator}Presenter\$InnerClass.html",
                description = "💣 **30%**")


        testRule(rule)
                .withAddedFile(file = "src/main/java/com/android/game/App.java")
                .withModifiedFile(file = "src/main/kotlin/com/android/game/Activity.kt")
                .withModifiedFile(file = "src/main/kotlin/com/android/game/Presenter.kt")
                .withModifiedFile(file = "build.gradle")
                .withDeletedFile(file = "src/main/kotlin/com/android/game/CustomView.kt")

                // Act
                .apply()

                // Assert
                .assertHasIssue(expectedIssue = expectedIssue1)
                .assertHasIssue(expectedIssue = expectedIssue2)
                .assertHasIssue(expectedIssue = expectedIssue3)
                .assertHasIssue(expectedIssue = expectedIssue4)
                .assertIssuesCount(expectedCount = 4)
    }

    @Test
    fun test_without_htmlFilePath() {

        // Arrange
        val rule = JacocoCoverageRule(
                classCoverageThreshold = 80,
                csvFilePath = "build/reports/jacoco/jacoco.csv",
                csvParser = csvParser,
                htmlFilePath = null
        )

        val expectedIssue = Issue(
                msg = "**com/android/game/App**",
                level = Level.INFO,
                filePath = "com.android.game${separator}App.html",
                description = "🏆 **95%**")

        testRule(rule)
                .withAddedFile(file = "src/main/java/com/android/game/App.java")

                // Act
                .apply()

                // Assert
                .assertHasIssue(expectedIssue = expectedIssue)
                .assertIssuesCount(expectedCount = 1)
    }


    @Test
    fun test_no_issues() {

        // Arrange
        val rule = JacocoCoverageRule(
                classCoverageThreshold = 80,
                csvFilePath = "build/reports/jacoco/jacoco.csv",
                csvParser = csvParser,
                htmlFilePath = null
        )


        testRule(rule)
                .withModifiedFile(file = "build.gradle")
                .withDeletedFile(file = "src/main/kotlin/com/android/game/CustomView.kt")

                // Act
                .apply()

                // Assert
                .assertNoIssues()
    }
}

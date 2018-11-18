package io.github.tarek360.rules.coverage

import io.github.tarek360.core.bold
import io.github.tarek360.rules.core.Issue
import io.github.tarek360.rules.core.Level
import io.github.tarek360.rules.coverage.JacocoCoverageRule.Companion.PROJECT_TOTAL_COVERAGE_TITLE
import io.github.tarek360.rules.test.testRule
import org.junit.Before
import org.junit.Test
import java.io.File.separator

class JacocoCoverageRuleTest {

    private lateinit var csvParser: CsvParser

    private val expectedTotalCoverage = Issue(
            msg = PROJECT_TOTAL_COVERAGE_TITLE.bold(),
            level = Level.ERROR("💣"),
            description = "56%".bold())

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
                                "com/android/game/Presenter\$InnerClass", 0)
                )
            }
        }
    }

    @Test
    fun test() {

        // Arrange

//        val htmlFilePath = "https://tarek360.github.io/koshry/build/reports"

        val rule = JacocoCoverageRule(
                classCoverageThreshold = 80,
                csvFilePath = "build/reports/jacoco/jacoco.csv",
//                htmlFilePath = htmlFilePath,
                csvParser = csvParser
        )

        val expectedIssue1 = Issue(
                msg = "com/android/game/App",
                level = Level.INFO("✅"),
                filePath = "src/main/java/com/android/game/App.java",
                description = "95%")

        val expectedIssue2 = Issue(
                msg = "com/android/game/Activity",
                level = Level.INFO("✅"),
                filePath = "src/main/kotlin/com/android/game/Activity.kt",
                description = "80%")

        val expectedIssue3 = Issue(
                msg = "com/android/game/Presenter",
                level = Level.ERROR("💣"),
                filePath = "src/main/kotlin/com/android/game/Presenter.kt",
                description = "50%")

        val expectedIssue4 = Issue(
                msg = "com/android/game/Presenter\$InnerClass",
                level = Level.ERROR("🔥"),
                filePath = "src/main/kotlin/com/android/game/Presenter.kt",
                description = "0%")


        testRule(rule)
                .withAddedFile(file = "src/main/java/com/android/game/App.java")
                .withModifiedFile(file = "src/main/kotlin/com/android/game/Activity.kt")
                .withModifiedFile(file = "src/main/kotlin/com/android/game/Presenter.kt")
                .withModifiedFile(file = "build.gradle")
                .withDeletedFile(file = "src/main/kotlin/com/android/game/CustomView.kt")

                // Act
                .apply()

                // Assert
                .assertFirstIssue(expectedIssue = expectedIssue1)
                .assertSecondIssue(expectedIssue = expectedIssue2)
                .assertThirdIssue(expectedIssue = expectedIssue3)
                .assertHasIssueAt(expectedIssue = expectedIssue4, position = 3)
                .assertLastIssue(expectedIssue = expectedTotalCoverage)
                .assertIssuesCount(expectedCount = 5)
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
                msg = "com/android/game/App",
                level = Level.INFO("✅"),
                filePath = "src/main/java/com/android/game/App.java",
                description = "95%")


        testRule(rule)
                .withAddedFile(file = "src/main/java/com/android/game/App.java")

                // Act
                .apply()

                // Assert
                .assertFirstIssue(expectedIssue = expectedIssue)
                .assertLastIssue(expectedIssue = expectedTotalCoverage)
                .assertIssuesCount(expectedCount = 2)
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
                .assertIssuesCount(expectedCount = 1) // only the total coverage
    }
}

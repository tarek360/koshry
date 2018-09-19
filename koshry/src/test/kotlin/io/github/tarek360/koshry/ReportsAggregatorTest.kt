package io.github.tarek360.koshry

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.whenever
import io.github.tarek360.core.mustEqual
import io.github.tarek360.koshry.url.FileUrlGenerator
import io.github.tarek360.rules.core.Issue
import io.github.tarek360.rules.core.Level
import io.github.tarek360.rules.core.Report
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ReportsAggregatorTest {

    @Mock
    private lateinit var fileUrlGenerator: FileUrlGenerator

    private val reportsAggregator by lazy {
        whenever(fileUrlGenerator.generate(any(), anyOrNull())).thenReturn("http://example.com/tarek3/path/file")
        ReportsAggregator(fileUrlGenerator)
    }

    @Test
    fun testAggregate() {

        // Arrange
        val list = listOf(

                Report(msgTitle = "Don't add any image files",
                        issues = arrayListOf(
                                Issue("JPG File", Level.ERROR(), "path/file"),
                                Issue("PNG File", Level.WARN(), "path/file"),
                                Issue("SVG File", Level.INFO())
                        )),

                Report(msgTitle = "Company Name Typo",
                        issues = arrayListOf(
                                Issue("Not koshory", Level.ERROR(), "path/file", 44)
                        ))
        )

        // Act
        val result = reportsAggregator.aggregate(list)

        // Assert
        result mustEqual "## Koshry Report\n" +
                "\n" +
                "|Status|Don't add any image files||\n" +
                "|:-:|-|:-:|\n" +
                "|❌|[JPG File](http://example.com/tarek3/path/file)||\n" +
                "|⚠️|[PNG File](http://example.com/tarek3/path/file)||\n" +
                "|ℹ️|SVG File||\n" +
                "\n" +
                "|Status|Company Name Typo||\n" +
                "|:-:|-|:-:|\n" +
                "|❌|[Not koshory](http://example.com/tarek3/path/file)||\n" +
                "\n"
    }

    @Test
    fun testAggregateEmptyReportsList() {
        // Arrange

        // Act
        val result = reportsAggregator.aggregate(emptyList())

        // Assert
        result mustEqual "## Koshry Report\n" +
                "\n" +
                "🎉 Congrats! Every thing is OK!"
    }
}

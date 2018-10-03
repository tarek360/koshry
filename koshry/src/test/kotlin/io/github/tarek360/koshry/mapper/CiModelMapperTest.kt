package io.github.tarek360.koshry.mapper

import com.nhaarman.mockitokotlin2.whenever
import io.github.tarek360.core.mustEqualAndNotNull
import io.github.tarek360.core.mustNotNull
import io.github.tarek360.core.mustNull
import org.junit.Test
import org.mockito.Mockito.mock

class CiModelMapperTest {

    @Test
    fun map() {
        // Arrange
        val ciModelMapper = CiModelMapper()
        val ci = mock(io.github.tarek360.ci.Ci::class.java)
        whenever(ci.gitHostToken).thenReturn("gitHostToken")
        whenever(ci.buildId).thenReturn(17)
        whenever(ci.projectId).thenReturn("projectId")
        whenever(ci.pullRequestId).thenReturn(3)

        // Act
        val result = ciModelMapper.map(ci)

        // Assert
        result.mustNotNull()
        result?.run {
            gitHostToken mustEqualAndNotNull "gitHostToken"
            buildId mustEqualAndNotNull 17
            projectId mustEqualAndNotNull "projectId"
            pullRequestId mustEqualAndNotNull 3
        }
    }

    @Test
    fun mapIfNull() {
        // Arrange
        val ciModelMapper = CiModelMapper()

        // Act
        val result = ciModelMapper.map(null)

        // Assert
        result.mustNull()
    }
}

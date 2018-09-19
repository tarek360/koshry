package io.github.tarek360.core

import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.*

infix fun Any.mustEqual(expected: Any) = assertEquals(expected, this)
infix fun Any.mustInstanceOf(expected: Class<*>) = assertThat(this, instanceOf(expected))

infix fun Any?.mustEqualAndNotNull(expected: Any) {
    assertNotNull(this)
    assertEquals(expected, this)
}

infix fun Collection<Any>.mustHaveSize(expected: Int) = assertEquals("unexpected size", expected, this.size)


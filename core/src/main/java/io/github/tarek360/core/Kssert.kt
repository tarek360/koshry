package io.github.tarek360.core

import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.*

infix fun Any.mustEqual(expected: Any) = assertEquals(expected, this)
infix fun Any.mustNotEqual(expected: Any) = assertNotEquals(expected, this)
fun Any?.mustNotNull() = assertNotNull(this)
fun Any?.mustNull() = assertNull(this)
infix fun Any.mustInstanceOf(expected: Class<*>) = assertThat(this, instanceOf(expected))

infix fun Any?.mustEqualAndNotNull(expected: Any) {
    assertNotNull(this)
    assertEquals(expected, this)
}

infix fun Collection<Any>.mustHaveSize(expected: Int) = assertEquals("unexpected size", expected, this.size)
fun Collection<Any>.mustEmpty() = this.mustHaveSize(0)

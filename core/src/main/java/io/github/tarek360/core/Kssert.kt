package io.github.tarek360.core

import org.junit.Assert.assertEquals

infix fun Any.mustEqual(expected: Any) = assertEquals(expected, this)
infix fun Collection<Any>.mustHaveSize(expected: Int) = assertEquals("unexpected size", expected, this.size)

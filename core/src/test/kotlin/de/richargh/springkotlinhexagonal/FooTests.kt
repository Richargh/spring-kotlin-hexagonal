package de.richargh.springkotlinhexagonal

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FooTests {

    @Test
    fun `foo should be equal to foo`() {
        assertThat("Foo").isEqualTo("Foo")
    }
}
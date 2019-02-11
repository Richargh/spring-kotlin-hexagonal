package de.richargh.springkotlinhexagonal

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.context.support.GenericApplicationContext

class ApplicationContextTest {

    @Test
    fun `application context can resolve the greeter`() {
        // arrange
        val applicationContext = GenericApplicationContext().apply {
            homeBeans().initialize(this)
            refresh()
        }

        // act
        val foo = applicationContext.getBean(Greeter::class.java)
        val greeting = foo.sayHello()

        // assert
        assertThat(greeting).isEqualTo("Hello")
    }

    @Test
    fun `application context can resolve the mock greeter`() {
        // arrange
        val applicationContext = GenericApplicationContext().apply {
            homeBeans().initialize(this)
            testHomeBeans().initialize(this)
            refresh()
        }

        // act
        val foo = applicationContext.getBean(Greeter::class.java)
        val greeting = foo.sayHello()

        // assert
        assertThat(greeting).isEqualTo("Moin")
    }

}
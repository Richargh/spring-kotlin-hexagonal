package de.richargh.springkotlinhexagonal

import de.richargh.springkotlinhexagonal.config.annotationTestConfig
import de.richargh.springkotlinhexagonal.config.functionalTestConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ApplicationContextTest {

    @Test
    fun `application context can resolve the greeter`() {
        // arrange
        val applicationContext = createContext()

        // act
        val foo = applicationContext.getBean(Greeter::class.java)
        val greeting = foo.sayHello()

        // assert
        assertThat(greeting).isEqualTo("Hello")
    }

    @Test
    fun `application context can resolve the mock greeter`() {
        // arrange
        val applicationContext = createContext(annotationTestConfig,
                                               functionalTestConfig)

        // act
        val foo = applicationContext.getBean(Greeter::class.java)
        val greeting = foo.sayHello()

        // assert
        assertThat(greeting).isEqualTo("Moin")
    }

}
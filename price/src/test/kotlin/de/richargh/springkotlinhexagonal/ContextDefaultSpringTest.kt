package de.richargh.springkotlinhexagonal

import de.richargh.springkotlinhexagonal.config.OurSpringTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources
import org.springframework.test.context.ActiveProfiles


@OurSpringTest
class DefaultContextSpringTest(@Autowired val applicationContext: ApplicationContext) {

    @Test
    fun `application context can resolve the default greeter`() {
        // arrange

        // act
        val foo = applicationContext.getBean(Greeter::class.java)
        val greeting = foo.sayHello()

        // assert
        assertThat(greeting).isEqualTo("Hello")
    }

    @Test
    fun `application context will resolve the property in replier if we supply the ApplicationProperties`() {
        // arrange
        val propertyValue = "default"
        val name = "Bob"

        // act
        val foo = applicationContext.getBean(Replier::class.java)
        val greeting = foo.speak("Bob")

        // assert
        assertThat(greeting).isEqualTo("Hello $name, $propertyValue greets you")
    }

}
package de.richargh.springkotlinhexagonal

import de.richargh.springkotlinhexagonal.config.FunctionalMockConfigInitializer
import de.richargh.springkotlinhexagonal.config.SpringContextTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource

class ContextSpringTest {

    @SpringContextTest
    @Nested
    inner class Default(@Autowired val applicationContext: ApplicationContext) {

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

    @TestPropertySource(locations = ["classpath:application-dev.properties"])
    @SpringContextTest
    @Nested
    inner class Dev(@Autowired val applicationContext: ApplicationContext) {

        @Test
        fun `application context will resolve the property in replier with the last supplied ApplicationProperties, even if the dev-Profile ist not active`() {
            // arrange
            val propertyValue = "dev"
            val name = "Bob"

            // act
            val foo = applicationContext.getBean(Replier::class.java)
            val greeting = foo.speak("Bob")

            // assert
            assertThat(greeting).isEqualTo("Hello $name, $propertyValue greets you")
        }
    }

    @SpringContextTest
    @ContextConfiguration(initializers = [FunctionalMockConfigInitializer::class])
    @Nested
    inner class Mock(@Autowired val applicationContext: ApplicationContext) {

        @Test
        fun `application context can resolve the mock greeter`() {
            // arrange

            // act
            val foo = applicationContext.getBean(Greeter::class.java)
            val greeting = foo.sayHello()

            // assert
            assertThat(greeting).isEqualTo("Moin")
        }
    }

    @Configuration
    @PropertySources(
            PropertySource("application.properties"))
    open class AnnotationSinglePropertyConfig

    @Configuration
    @PropertySources(
            PropertySource("application.properties"),
            PropertySource("application-dev.properties"))
    open class AnnotationOverridingPropertyConfig
}
package de.richargh.springkotlinhexagonal

import de.richargh.springkotlinhexagonal.config.functionalTestOverridingMockConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources

class ContextTest {

    @Test
    fun `application context can resolve the default greeter`() {
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
        val applicationContext = createContext(emptyArray(), arrayOf(functionalTestOverridingMockConfig()))

        // act
        val foo = applicationContext.getBean(Greeter::class.java)
        val greeting = foo.sayHello()

        // assert
        assertThat(greeting).isEqualTo("Moin")
    }

    @Test
    fun `application context will resolve the property in replier if we supply the ApplicationProperties`() {
        // arrange
        val propertyValue = "default"
        val name = "Bob"
        val applicationContext = createContext(arrayOf(AnnotationSinglePropertyConfig::class.java))

        // act
        val foo = applicationContext.getBean(Replier::class.java)
        val greeting = foo.speak("Bob")

        // assert
        assertThat(greeting).isEqualTo("Hello $name, $propertyValue greets you")
    }

    @Test
    fun `application context will resolve the property in replier with the last supplied ApplicationProperties, even if the dev-Profile ist not active`() {
        // arrange
        val propertyValue = "dev"
        val name = "Bob"
        val applicationContext = createContext(arrayOf(AnnotationOverridingPropertyConfig::class.java))

        // act
        val foo = applicationContext.getBean(Replier::class.java)
        val greeting = foo.speak("Bob")

        // assert
        assertThat(greeting).isEqualTo("Hello $name, $propertyValue greets you")
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
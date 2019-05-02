package de.richargh.springkotlinhexagonal

import de.richargh.springkotlinhexagonal.config.OurSpringTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource

@TestPropertySource(locations = ["classpath:application-dev.properties"])
@OurSpringTest
class DevContextSpringTest(@Autowired val applicationContext: ApplicationContext) {

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
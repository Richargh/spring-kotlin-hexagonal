package de.richargh.springkotlinhexagonal

import de.richargh.springkotlinhexagonal.config.*
import de.richargh.springkotlinhexagonal.domain.Greeter
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.test.context.ContextConfiguration

class RunningContextSpringTest() {

    @SpringRunningContextTest
    @ContextConfiguration(initializers = [FunctionalProductionConfigInitializer::class])
    @Nested
    inner class Default(@Autowired private val restTemplate: TestRestTemplate,
                        @Autowired private val applicationContext: ApplicationContext) {

        @Test
        fun `the reply page should use the name from the default ApplicationProperties, when we don't supply a profile`() {
            // Arrange
            val propertyValue = "default"

            // Act
            val entity = restTemplate.getForEntity("/reply?name=Jim", String::class.java)

            // Assert
            assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(entity.body).contains("$propertyValue greets you")
        }
    }

    @SpringRunningContextTest
    @ContextConfiguration(initializers = [FunctionalProductionConfigInitializer::class, FunctionalMockConfigInitializer::class])
    @Nested
    inner class Dev(@Autowired private val restTemplate: TestRestTemplate,
                    @Autowired private val applicationContext: ApplicationContext) {

        @Test
        fun `the application bean should be mocked`() {
            // Arrange
            val bean1 = applicationContext.getBean(Greeter::class.java)

            // Act
            val saying = bean1.sayHello()

            // Assert
            Assertions.assertThat(saying).isEqualTo("Moin")
        }

        @Test
        fun `the index page should use the mock bean`() {
            // Arrange

            // Act
            val entity = restTemplate.getForEntity("/", String::class.java)

            // Assert
                         //assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(entity.body).contains("<span>Moin</span>")
        }

        @Test
        fun `the reply page should reply to the name`() {
            // Arrange
            val name = "Jim"

            // Act
            val entity = restTemplate.getForEntity("/reply?name=$name", String::class.java)

            // Assert
            assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
            assertThat(entity.body).contains(name)
        }
    }
}
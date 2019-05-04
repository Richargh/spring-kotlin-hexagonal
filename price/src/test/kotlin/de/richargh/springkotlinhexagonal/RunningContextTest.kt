package de.richargh.springkotlinhexagonal

import de.richargh.springkotlinhexagonal.config.functionalTestOverridingMockConfig
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class RunningContextTest {

    @Nested
    inner class Default {

        @Test
        fun `the reply page should use the name from the default ApplicationProperties, when we don't supply a profile`() {
            // Arrange
            val propertyValue = "default"

            // Act
            val htmlPath = given().port(defaultPort)
                    .`when`()
                    .get("/reply?name=Jim")
                    .then()
                    .statusCode(200)
                    .contentType(ContentType.HTML)
                    .extract()
                    .body().htmlPath()

            // Assert
            assertThat(htmlPath.getString("html.body.div.div.h2.span")).contains("$propertyValue greets you")
        }
    }

    @Nested
    inner class Dev {

        @Test
        fun `the header of the index page should be correct`() {
            // Arrange

            // Act
            val htmlPath = given().port(devPort)
                    .`when`()
                    .get("/")
                    .then()
                    .statusCode(200)
                    .contentType(ContentType.HTML)
                    .extract()
                    .body().htmlPath()

            // Assert
            assertThat(htmlPath.getString("html.head.title")).isEqualTo("Spring Boot Thymeleaf Hello Price")
        }

        @Test
        fun `the application bean should be mocked`() {
            // Arrange
            val bean1 = applicationDevContext.getBean(Greeter::class.java)

            // Act
            val saying = bean1.sayHello()

            // Assert
            assertThat(saying).isEqualTo("Moin")
        }

        @Test
        fun `the index page should use the mock bean`() {
            // Arrange

            // Act
            val htmlPath = given().port(devPort)
                    .`when`()
                    .get("/")
                    .then()
                    .statusCode(200)
                    .contentType(ContentType.HTML)
                    .extract()
                    .body().htmlPath()

            // Assert
            assertThat(htmlPath.getString("html.body.div.div.h2.span")).isEqualTo("Moin")
        }

        @Test
        fun `the reply page should use the name from the dev ApplicationProperties, when we use the dev profile`() {
            // Arrange
            val propertyValue = "dev"

            // Act
            val htmlPath = given().port(devPort)
                    .`when`()
                    .get("/reply?name=Jim")
                    .then()
                    .statusCode(200)
                    .contentType(ContentType.HTML)
                    .extract()
                    .body().htmlPath()

            // Assert
            assertThat(htmlPath.getString("html.body.div.div.h2.span")).contains("$propertyValue greets you")
        }

        @Test
        fun `the reply page should reply to the name`() {
            // Arrange
            val name = "Jim"

            // Act
            val htmlPath = given().port(devPort)
                    .`when`()
                    .get("/reply?name=$name")
                    .then()
                    .statusCode(200)
                    .contentType(ContentType.HTML)
                    .extract()
                    .body().htmlPath()

            // Assert
            assertThat(htmlPath.getString("html.body.div.div.h2.span")).contains(name)
        }
    }

    companion object {

        private val defaultPort = 8096
        private val applicationDefaultContext = createRunningContext(
                args = arrayOf("--server.port=$defaultPort"),
                additionalAnnotationConfig = emptyArray(),
                additionalFunctionalConfig = arrayOf(functionalTestOverridingMockConfig()))

        private val devPort = 8095
        private val applicationDevContext = createRunningContext(
                args = arrayOf("--server.port=$devPort", "--spring.profiles.active=dev"),
                additionalAnnotationConfig = emptyArray(),
                additionalFunctionalConfig = arrayOf(functionalTestOverridingMockConfig()))
    }
}
package de.richargh.springkotlinhexagonal

import de.richargh.springkotlinhexagonal.config.annotationTestConfig
import de.richargh.springkotlinhexagonal.config.functionalTestConfig
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RunningApplicationTest {

    @Test
    fun `the header of the index page should be correct`() {
        // Arrange

        // Act
        val htmlPath = given().port(port)
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
        val bean1 = applicationContext.getBean(Greeter::class.java)

        // Act
        val saying = bean1.sayHello()

        // Assert
        assertThat(saying).isEqualTo("Moin")
    }

    @Test
    fun `the index page should use the mock bean`() {
        // Arrange

        // Act
        val htmlPath = given().port(port)
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
    fun `the reply page should reply to the name`() {
        // Arrange
        val name = "Jim"

        // Act
        val htmlPath = given().port(port)
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

    @Test
    fun `the reply page should use the name from the dev property`() {
        // Arrange

        // Act
        val htmlPath = given().port(port)
                .`when`()
                .get("/reply?name=Jim")
                .then()
                .statusCode(200)
                .contentType(ContentType.HTML)
                .extract()
                .body().htmlPath()

        // Assert
        assertThat(htmlPath.getString("html.body.div.div.h2.span")).contains("dev")
    }

    companion object {

        private val port = 8095
        private val applicationContext = createRunningContext(
                arrayOf("--server.port=$port"),
                annotationTestConfig,
                functionalTestConfig)
    }

}
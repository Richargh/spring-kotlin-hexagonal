package de.richargh.springkotlinhexagonal

import io.mockk.every
import io.mockk.mockk
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.support.beans

class ApplicationTest {

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
        val bean1 = applicationContext.getBean(Foo::class.java)
        assertThat(bean1.sayHello()).isEqualTo("Moin")
        assertThat(htmlPath.getString("html.head.title")).isEqualTo("Spring Boot Thymeleaf Hello Price")
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
        val bean1 = applicationContext.getBean(Foo::class.java)
        assertThat(bean1.sayHello()).isEqualTo("Moin")
        assertThat(htmlPath.getString("html.body.div.div.h2.span")).isEqualTo("Message: Moin")
    }


    @Test
    fun `the application bean should be mocked`() {
        // Arrange
        val bean1 = applicationContext.getBean(Foo::class.java)

        // Act
        val saying = bean1.sayHello()

        // Assert
        assertThat(saying).isEqualTo("Moin")
    }

    companion object {

        private val port = 8095
        private val applicationContext = SpringApplicationBuilder()
                .initializers(homeBeans(), testHomeBeans())
                .sources(Application::class.java)
                .run("--server.port=$port")
    }

}

fun testHomeBeans() = beans {
    bean<Foo>{
        val foo = mockk<Foo>(relaxed = true)
        every { foo.sayHello() } returns "Moin"
        foo
    }
}
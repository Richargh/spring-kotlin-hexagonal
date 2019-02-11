package de.richargh.springkotlinhexagonal

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.builder.SpringApplicationBuilder

class SomeTest {

    @Test
    fun `foo should be equal to foo`() {
        val applicationContext = SpringApplicationBuilder()
                .initializers(testHomeBeans(), homeBeans())
                .sources(Application::class.java)
                .run()

        println("Started Application")

        val bean1 = applicationContext.getBean(Foo::class.java)

        assertThat(bean1.sayHello()).isEqualTo("Hello")
    }
}
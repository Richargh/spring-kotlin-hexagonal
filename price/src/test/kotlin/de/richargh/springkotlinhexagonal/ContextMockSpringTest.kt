package de.richargh.springkotlinhexagonal

import de.richargh.springkotlinhexagonal.config.FunctionalMockConfigInitializer
import de.richargh.springkotlinhexagonal.config.OurSpringTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration

@OurSpringTest
@ContextConfiguration(initializers = [FunctionalMockConfigInitializer::class])
class MockContextSpringTest(@Autowired val applicationContext: ApplicationContext) {

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
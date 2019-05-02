package de.richargh.springkotlinhexagonal

import de.richargh.springkotlinhexagonal.config.FunctionalMockConfigInitializer
import de.richargh.springkotlinhexagonal.config.OurSpringTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath

@AutoConfigureMockMvc
@WebMvcTest
@ContextConfiguration(initializers = [FunctionalMockConfigInitializer::class])
@OurSpringTest
class RunningContextMockSpringTest(
        @Autowired private val mockMvc: MockMvc,
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

    @Disabled("does not work yet")
    @Test
    fun `the index page should use the mock bean`() {
        // Arrange

        // Act
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                // Assert
                .andExpect(xpath("/html/body").string("Moin"))
    }

    @Disabled("does not work yet")
    @Test
    fun `the reply page should reply to the name`() {
        // Arrange
        val name = "Jim"

        // Act
        mockMvc.perform(MockMvcRequestBuilders.get("/reply?name=$name"))
                // Assert
                .andExpect(xpath("/html/body/div/div/h2/span").string(name))
    }

}
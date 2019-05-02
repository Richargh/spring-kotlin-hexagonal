package de.richargh.springkotlinhexagonal

import de.richargh.springkotlinhexagonal.config.OurSpringTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath

@AutoConfigureMockMvc
@WebMvcTest
@OurSpringTest
class RunningContextDefaultSpringTest(@Autowired private val mockMvc: MockMvc) {

    @Test
    fun `the header of the index page should be correct`() {
        // Arrange

        // Act
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
        // Assert
                .andExpect(xpath("/html/head/title").string("Spring Boot Thymeleaf Hello Price"))
    }

}
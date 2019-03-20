package de.richargh.springkotlinhexagonal

import de.richargh.springkotlinhexagonal.config.PropertyConfig
import de.richargh.springkotlinhexagonal.properties.GreeterProperties
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.TypeExcludeFilter
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.stereotype.Controller

class ApplicationPropertiesTest {

    @Test
    fun `with PropertyConfig properties are read correctly from application properties`() {
        // arrange
        val nameToSpeak = "Foo"
        val personInProperties = "blub"
        val propertyConfig: Array<Class<out Any>> = arrayOf(PropertyConfig::class.java)

        val (speakerActual, runningSpeakerActual) =
            createContext(propertyConfig).use { context ->
            createRunningContext(propertyConfig).use { runningContext ->
            // act
            val speakerActual = context.getBean(Speaker::class.java).speak(nameToSpeak)
            val runningSpeakerActual = runningContext.getBean(Speaker::class.java).speak(nameToSpeak)
            speakerActual to runningSpeakerActual
        }}

        // assert
        assertThat(runningSpeakerActual).isEqualTo("Hello $nameToSpeak, $personInProperties greets you")
        assertThat(runningSpeakerActual).isEqualTo(speakerActual)
    }

    @Test
    fun `test is red without PropertyConfig application properties are not found`() {
        // arrange
        val nameToSpeak = "Foo"
        val personInProperties = "blub"
        val propertyConfig: Array<Class<out Any>> = emptyArray()

        val runningSpeakerActual = createRunningContext(propertyConfig).use { runningContext ->
            // act
            runningContext.getBean(Speaker::class.java).speak(nameToSpeak)
        }

        // assert
        assertThat(runningSpeakerActual).isEqualTo("Hello $nameToSpeak, $personInProperties greets you")
    }

    @Test
    fun `with @ComponentScan properties are read correctly from application properties`() {
        // arrange
        val nameToSpeak = "Foo"
        val personInProperties = "blub"
        val springApplicationBuilder = SpringApplicationBuilder()
                .sources(ApplicationWithComponentScan::class.java)
                .initializers(*beans())

        val runningSpeakerActual = springApplicationBuilder.run().use { runningContext ->
            // act
            runningContext.getBean(Speaker::class.java).speak(nameToSpeak)
        }

        // assert
        assertThat(runningSpeakerActual).isEqualTo("Hello $nameToSpeak, $personInProperties greets you")
    }


    @ComponentScan(excludeFilters = [
        ComponentScan.Filter(type = FilterType.CUSTOM, classes = [TypeExcludeFilter::class]),
        ComponentScan.Filter(type = FilterType.CUSTOM, classes = [AutoConfigurationExcludeFilter::class]),
        ComponentScan.Filter(type = FilterType.ANNOTATION, classes = [Controller::class])
    ])
    @SpringBootConfiguration
    @EnableAutoConfiguration(exclude = [CassandraDataAutoConfiguration::class])
    @EnableConfigurationProperties(GreeterProperties::class)
    open class ApplicationWithComponentScan

}
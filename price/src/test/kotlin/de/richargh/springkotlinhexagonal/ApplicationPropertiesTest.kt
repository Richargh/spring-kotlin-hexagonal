package de.richargh.springkotlinhexagonal

import de.richargh.springkotlinhexagonal.config.PropertyConfig
import de.richargh.springkotlinhexagonal.properties.GreeterProperties
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
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
        val context = createContext(propertyConfig)
        val runningContext = createRunningContext(propertyConfig)

        // act
        val speaker = context.getBean(Speaker::class.java)
        val runningSpeaker = runningContext.getBean(Speaker::class.java)

        // assert
        assertThat(runningSpeaker.speak(nameToSpeak)).isEqualTo("Hello $nameToSpeak, $personInProperties greets you")
        assertThat(runningSpeaker.speak(nameToSpeak)).isEqualTo(speaker.speak(nameToSpeak))
    }

    @Test
    fun `without PropertyConfig application properties are not found`() {
        // arrange
        val propertyConfig: Array<Class<out Any>> = emptyArray()

        // act, assert
        assertThatThrownBy{
            createRunningContext(propertyConfig)
        }.isInstanceOf(UninitializedPropertyAccessException::class.java)
    }

    @Test
    fun `with @ComponentScan properties are read correctly from application properties`() {
        // arrange
        val nameToSpeak = "Foo"
        val personInProperties = "blub"
        val runningContext = SpringApplicationBuilder()
                .sources(ApplicationWithComponentScan::class.java)
                .initializers(*beans())
                .run()

        // act
        val runningSpeaker = runningContext.getBean(Speaker::class.java)

        // assert
        assertThat(runningSpeaker.speak(nameToSpeak)).isEqualTo("Hello $nameToSpeak, $personInProperties greets you")
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
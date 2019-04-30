package de.richargh.springkotlinhexagonal

import de.richargh.springkotlinhexagonal.config.AnnotationPropertyConfig
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
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.stereotype.Controller

class ApplicationPropertiesTest {

    @Test
    fun `foo`() {
        val nameToSpeak = "Foo"
        val (speakerActual, runningSpeakerActual) = createRunningContext().use { context ->
            val speakerActual = context.getBean(Replier::class.java).speak(nameToSpeak)
            val runningSpeakerActual = context.getBean(Replier::class.java).speak(nameToSpeak)
            speakerActual to runningSpeakerActual
        }

        // assert
        assertThat(runningSpeakerActual).isEqualTo(speakerActual)
    }

    @Test
    fun `with PropertyConfig properties are read correctly from application properties`() {
        // arrange
        val nameToSpeak = "Foo"
        val personInProperties = "blub"
        val testConfig: Array<Class<out Any>> = arrayOf(AnnotationPropertyConfig::class.java)

        val (speakerActual, runningSpeakerActual) =
                createContext(testConfig).use { context ->
                    createRunningContext(testConfig).use { runningContext ->
                        // act
                        val speakerActual = context.getBean(Replier::class.java).speak(nameToSpeak)
                        val runningSpeakerActual = runningContext.getBean(Replier::class.java).speak(nameToSpeak)
                        speakerActual to runningSpeakerActual
                    }
                }

        // assert
        assertThat(runningSpeakerActual).isEqualTo("Hello $nameToSpeak, $personInProperties greets you")
        assertThat(runningSpeakerActual).isEqualTo(speakerActual)
    }

    @Test
    fun `test is red without PropertyConfig application properties are not found`() {
        // arrange
        val nameToSpeak = "Foo"
        val personInProperties = "blub"

        val runningSpeakerActual = createRunningContext().use { runningContext ->
            // act
            runningContext.getBean(Replier::class.java).speak(nameToSpeak)
        }

        // assert
        assertThat(runningSpeakerActual).isEqualTo("Hello $nameToSpeak, $personInProperties greets you")
    }

    @Test
    fun `with @ComponentScan properties are read correctly from application properties`() {
        // arrange
        val nameToSpeak = "Foo"
        val personInProperties = "blub"

        val runningSpeakerActual = createRunningContext().use { runningContext ->
            // act
            runningContext.getBean(Replier::class.java).speak(nameToSpeak)
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
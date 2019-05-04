package de.richargh.springkotlinhexagonal

import de.richargh.springkotlinhexagonal.config.AnnotationGreeterConfig
import de.richargh.springkotlinhexagonal.config.AnnotationPlaceholderConfig
import de.richargh.springkotlinhexagonal.config.functionalHomeConfig
import de.richargh.springkotlinhexagonal.properties.GreeterProperties
import de.richargh.springkotlinhexagonal.properties.printProperties
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Import

@Import(NoCassandraAnnotationConfig::class,
        AnnotationPlaceholderConfig::class,
        AnnotationGreeterConfig::class)
@EnableAutoConfiguration
@SpringBootConfiguration
@EnableConfigurationProperties(GreeterProperties::class)
open class Application

/**
 * This probably seems strange, why add cassandra starter if we exclude it anyway?
 * The point is to show how to exclude AutoConfigurations if the need arises
 */
@EnableAutoConfiguration(exclude = [CassandraDataAutoConfiguration::class])
open class NoCassandraAnnotationConfig


internal fun functionalProductionConfig() = arrayOf(
        functionalHomeConfig())

fun main(args: Array<String>) {
    printProperties()
    createRunningContext(args)
}


package de.richargh.springkotlinhexagonal

import de.richargh.springkotlinhexagonal.config.AnnotationGreeterConfig
import de.richargh.springkotlinhexagonal.config.AnnotationPlaceholderConfig
import de.richargh.springkotlinhexagonal.config.functionalHomeConfig
import de.richargh.springkotlinhexagonal.properties.GreeterProperties
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.support.BeanDefinitionDsl
import java.util.*

@EnableAutoConfiguration
@SpringBootConfiguration
@EnableConfigurationProperties(GreeterProperties::class)
open class Application

@EnableAutoConfiguration(exclude = [CassandraDataAutoConfiguration::class])
open class NoCassandraApplication

fun main(args: Array<String>) {
    printProperties()
    createRunningContext(args)
}

internal fun createContext(
        additionalAnnotationConfig: Array<Class<out Any>> = emptyArray(),
        additionalFunctionalConfig: Array<BeanDefinitionDsl> = emptyArray()): ConfigurableApplicationContext {
    val context = AnnotationConfigApplicationContext()
    annotationProductionConfig().forEach { context.register(it) }
    additionalAnnotationConfig.forEach { context.register(it) }

    functionalProductionConfig().forEach { it.initialize(context) }
    additionalFunctionalConfig.forEach { it.initialize(context) }

    context.refresh()
    return context
}

internal fun createRunningContext(
        args: Array<String> = emptyArray(),
        additionalAnnotationConfig: Array<Class<out Any>> = emptyArray(),
        additionalFunctionalConfig: Array<BeanDefinitionDsl> = emptyArray()) = runApplication<Application>(*args) {
    addPrimarySources(annotationProductionConfig().toList())
    addPrimarySources(additionalAnnotationConfig.toList())

    addInitializers(*functionalProductionConfig())
    addInitializers(*additionalFunctionalConfig)
}

internal fun annotationProductionConfig() = arrayOf(
        Application::class.java,
        NoCassandraApplication::class.java,

        AnnotationPlaceholderConfig::class.java,
        AnnotationGreeterConfig::class.java)

internal fun functionalProductionConfig() = arrayOf(
        functionalHomeConfig())

private fun printProperties() {
    val applicationProperties = Properties().apply {
        load(Thread.currentThread().contextClassLoader.getResourceAsStream("application.properties"))
    }
    val devProperties = Properties().apply {
        load(Thread.currentThread().contextClassLoader.getResourceAsStream("application-dev.properties"))
    }
    val key = "price.greeter.person"

    println("Application.properties has property value: ${applicationProperties.getProperty(key)}")
    println("Application-dev.properties has property value: ${devProperties.getProperty(key)}")
}

package de.richargh.springkotlinhexagonal

import de.richargh.springkotlinhexagonal.config.PropertyConfig
import de.richargh.springkotlinhexagonal.config.SpeakerConfig
import de.richargh.springkotlinhexagonal.config.configBeans
import de.richargh.springkotlinhexagonal.config.homeBeans
import de.richargh.springkotlinhexagonal.properties.GreeterProperties
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.*
import java.util.*
import org.springframework.context.ConfigurableApplicationContext

@SpringBootConfiguration
@EnableAutoConfiguration
@EnableConfigurationProperties(GreeterProperties::class)
open class Application

@EnableAutoConfiguration(exclude = [CassandraDataAutoConfiguration::class])
open class NoCassandraApplication


fun main(args: Array<String>) {
    printProperties()
    createRunningContext(arrayOf(PropertyConfig::class.java), args)
}

internal fun createContext(extraClasses: Array<Class<out Any>>): ConfigurableApplicationContext {
    val ctx = AnnotationConfigApplicationContext()
    configurations().forEach{ ctx.register(it) }
    beans().forEach { it.initialize(ctx) }

    extraClasses.forEach { ctx.register(it) }

    ctx.refresh()
    return ctx
}

internal fun createRunningContext(extraClasses: Array<Class<out Any>>, args: Array<String> = emptyArray()): ConfigurableApplicationContext{
    val applicationContext = SpringApplicationBuilder()
            .sources(*extraClasses, *configurations())
            .initializers(*beans())
            .run(*args)
    return applicationContext
}

internal fun configurations() = arrayOf(
        Application::class.java,
        NoCassandraApplication::class.java,

        SpeakerConfig::class.java
)

internal fun beans() = arrayOf(
        homeBeans(),
        configBeans()
)

private fun printProperties(){
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

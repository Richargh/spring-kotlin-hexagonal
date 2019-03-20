package de.richargh.springkotlinhexagonal

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.*
import java.util.*
import org.springframework.context.ConfigurableApplicationContext

@EnableConfigurationProperties(MixitProperties::class)
@SpringBootConfiguration
@EnableAutoConfiguration
open class Application

@EnableAutoConfiguration(exclude = [CassandraDataAutoConfiguration::class])
open class NoCassandraApplication


fun configurations() = arrayOf(
        Application::class.java,
        NoCassandraApplication::class.java,
        SpeakerConfig::class.java,
        PropertyConfig::class.java
)

fun beans() = arrayOf(
        homeBeans(),
        configBeans()
)

fun createContext(): ConfigurableApplicationContext {
    val ctx = AnnotationConfigApplicationContext()
    configurations().forEach{ ctx.register(it) }
    beans().forEach { it.initialize(ctx) }
//    ctx.refresh()

//    val speaker = ctx.getBean(Speaker::class.java)
//    println("Speaker: "+speaker.speak("Humbert"))
//
//    val homeController = ctx.getBean(HomeController::class.java)
//    println("HomeController: "+homeController.index(mutableMapOf("" to "")))

    return ctx
}

fun createApplication(args: Array<String>): ConfigurableApplicationContext{
    val applicationContext = SpringApplicationBuilder()
            .sources(*configurations())
            .initializers(*beans())
            .run(*args)
    return applicationContext
}

fun checkPropertiesManually(){
    val properties = Properties()
    properties.load(Thread.currentThread().contextClassLoader.getResourceAsStream("application.properties"))
    println("Loaded person from properties ${properties.getProperty("price.greeter.person")}")
}

fun main(args: Array<String>) {
    val context = createContext()
    createApplication(args)



//    val greeter = applicationContext.getBean(Greeter::class.java)
//    println(greeter.sayHello())
//    val speaker = applicationContext.getBean(Speaker::class.java)
//    println(speaker.speak("Humbert"))

}


@Configuration
open class SpeakerConfig {
    @Bean
    open fun speaker() = Speaker()
}

@Configuration
@PropertySources(
    PropertySource("application.properties"),
    PropertySource("application-dev.properties")
)
open class PropertyConfig
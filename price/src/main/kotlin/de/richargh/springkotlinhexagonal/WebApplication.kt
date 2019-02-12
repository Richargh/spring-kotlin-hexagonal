package de.richargh.springkotlinhexagonal

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder

@EnableAutoConfiguration
open class Application

fun main(args: Array<String>) {
    val applicationContext = SpringApplicationBuilder()
            .initializers(homeBeans())
            .sources(Application::class.java, NoCassandraApplication::class.java)
            .run(*args)

    val bean1 = applicationContext.getBean(Greeter::class.java)
    println(bean1.sayHello())
}

@EnableAutoConfiguration(exclude = [CassandraDataAutoConfiguration::class])
open class NoCassandraApplication
package de.richargh.springkotlinhexagonal.properties

import java.util.*

internal fun printProperties() {
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
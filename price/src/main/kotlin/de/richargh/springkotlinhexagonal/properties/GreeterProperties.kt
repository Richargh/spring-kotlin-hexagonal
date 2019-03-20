package de.richargh.springkotlinhexagonal.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("price.greeter")
class GreeterProperties{
    lateinit var person: String
}
package de.richargh.springkotlinhexagonal

import org.springframework.beans.factory.annotation.Value

class Speaker{

    @Value("%{price.greeter.person}")
    private val person: String = ""

    fun speak(name: String) = "Hello $name, $person greets you"
}
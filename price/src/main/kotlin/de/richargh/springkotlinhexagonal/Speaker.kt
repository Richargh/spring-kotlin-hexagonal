package de.richargh.springkotlinhexagonal

import de.richargh.springkotlinhexagonal.properties.GreeterProperties

class Speaker(val greeterProperties: GreeterProperties){

    fun speak(name: String) = "Hello $name, ${greeterProperties.person} greets you"
}
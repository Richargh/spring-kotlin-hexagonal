package de.richargh.springkotlinhexagonal

import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.context.support.beans

fun configBeans() = beans {
    bean {
        PropertySourcesPlaceholderConfigurer().apply { setPlaceholderPrefix("%{") }
    }
}
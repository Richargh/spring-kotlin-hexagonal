package de.richargh.springkotlinhexagonal.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

@Configuration
open class PlaceholderConfig{
    @Bean
    open fun propertyPlaceholder() = PropertySourcesPlaceholderConfigurer().apply { setPlaceholderPrefix("%{") }
}
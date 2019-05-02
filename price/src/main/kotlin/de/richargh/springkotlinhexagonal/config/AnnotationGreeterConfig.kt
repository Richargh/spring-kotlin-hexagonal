package de.richargh.springkotlinhexagonal.config

import de.richargh.springkotlinhexagonal.Greeter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class AnnotationGreeterConfig {
    @Bean
    open fun greeter() = Greeter()
}
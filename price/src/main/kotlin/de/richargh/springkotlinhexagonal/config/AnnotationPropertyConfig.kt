package de.richargh.springkotlinhexagonal.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources

@Configuration
@PropertySources(
        PropertySource("application.properties"),
        PropertySource("application-dev.properties")
                )
open class AnnotationPropertyConfig
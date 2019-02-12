package de.richargh.springkotlinhexagonal.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource

@Configuration
open class PropertySourcesConfig {

    object DefaultConfig {

        private val PROPERTIES_RESOURCES = arrayOf(ClassPathResource("example.properties"))
        
        @Bean
        fun propertySourcesPlaceholderConfigurer(): PropertySourcesPlaceholderConfigurer {
            val pspc = PropertySourcesPlaceholderConfigurer()
            pspc.setLocations(*PROPERTIES_RESOURCES)
            return pspc
        }
    }
}
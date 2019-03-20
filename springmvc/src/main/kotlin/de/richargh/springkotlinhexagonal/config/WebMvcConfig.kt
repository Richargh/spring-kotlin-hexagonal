package de.richargh.springkotlinhexagonal.config

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.servlet.config.annotation.*
import org.thymeleaf.spring5.SpringTemplateEngine
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.spring5.view.ThymeleafViewResolver
import org.thymeleaf.templatemode.TemplateMode

@Configuration
@EnableWebMvc
open class WebMvcConfig: WebMvcConfigurer,
                         ApplicationContextAware {

    private var applicationContext: ApplicationContext? = null

    override fun setApplicationContext(applicationContext: ApplicationContext?) {
        this.applicationContext = applicationContext
    }

    @Bean
    open fun templateResolver() = SpringResourceTemplateResolver().apply {
        prefix = "/pages/"
        suffix = ".html"
        templateMode = TemplateMode.HTML
        setApplicationContext(applicationContext)
    }

    @Bean
    open fun templateEngine() = SpringTemplateEngine().apply { setTemplateResolver(templateResolver()) }

    @Bean
    open fun viewResolver() = ThymeleafViewResolver().apply {
        templateEngine = templateEngine()
        order = 1
    }

    override fun configureMessageConverters(converters: List<HttpMessageConverter<*>>?) {
        super.configureMessageConverters(converters)
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry?) {
        registry!!.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(31556926)
        registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(31556926)
        registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(31556926)
    }
}
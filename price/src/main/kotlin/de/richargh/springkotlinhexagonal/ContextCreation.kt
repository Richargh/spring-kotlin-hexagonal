package de.richargh.springkotlinhexagonal

import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.support.BeanDefinitionDsl

internal fun createContext(
        additionalAnnotationConfig: Array<Class<out Any>> = emptyArray(),
        additionalFunctionalConfig: Array<BeanDefinitionDsl> = emptyArray()): ConfigurableApplicationContext {
    val context = AnnotationConfigApplicationContext()
    context.register(Application::class.java)
    additionalAnnotationConfig.forEach { context.register(it) }

    functionalProductionConfig().forEach { it.initialize(context) }
    additionalFunctionalConfig.forEach { it.initialize(context) }

    context.refresh()
    return context
}

internal fun createRunningContext(
        args: Array<String> = emptyArray(),
        additionalAnnotationConfig: Array<Class<out Any>> = emptyArray(),
        additionalFunctionalConfig: Array<BeanDefinitionDsl> = emptyArray()) =
        runApplication<Application>(*args) {
            addPrimarySources(listOf(Application::class.java))
            addPrimarySources(additionalAnnotationConfig.toList())

            addInitializers(*functionalProductionConfig())
            addInitializers(*additionalFunctionalConfig)
        }
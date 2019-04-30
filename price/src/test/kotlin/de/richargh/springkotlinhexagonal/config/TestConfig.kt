package de.richargh.springkotlinhexagonal.config

import de.richargh.springkotlinhexagonal.NoCassandraApplication
import org.springframework.context.support.BeanDefinitionDsl

val annotationTestConfig: Array<Class<out Any>> = arrayOf(
        NoCassandraApplication::class.java)

val functionalTestConfig: Array<BeanDefinitionDsl> = arrayOf(
        functionalTestOverridingMockConfig())

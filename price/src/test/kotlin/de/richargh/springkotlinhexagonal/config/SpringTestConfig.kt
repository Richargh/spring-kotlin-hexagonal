package de.richargh.springkotlinhexagonal.config

import de.richargh.springkotlinhexagonal.Application
import de.richargh.springkotlinhexagonal.NoCassandraApplication
import de.richargh.springkotlinhexagonal.functionalProductionConfig
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.annotation.Import
import org.springframework.context.support.GenericApplicationContext
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@ContextConfiguration(initializers = [FunctionalProductionConfigInitializer::class], classes = [TestApplicationConfiguration::class])
@TestPropertySource(locations = ["classpath:application.properties"])
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
annotation class OurSpringTest

class FunctionalProductionConfigInitializer: ApplicationContextInitializer<GenericApplicationContext> {
    override fun initialize(context: GenericApplicationContext) {
        functionalProductionConfig().forEach { it.initialize(context) }
    }
}

class FunctionalMockConfigInitializer: ApplicationContextInitializer<GenericApplicationContext> {
    override fun initialize(context: GenericApplicationContext) {
        functionalTestOverridingMockConfig().initialize(context)
    }
}

@TestConfiguration
@Import(value = [
    Application::class,
    NoCassandraApplication::class,

    AnnotationGreeterConfig::class
])
open class TestApplicationConfiguration
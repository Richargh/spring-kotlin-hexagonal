package de.richargh.springkotlinhexagonal.config

import de.richargh.springkotlinhexagonal.Application
import de.richargh.springkotlinhexagonal.NoCassandraAnnotationConfig
import de.richargh.springkotlinhexagonal.functionalProductionConfig
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.annotation.Import
import org.springframework.context.support.GenericApplicationContext
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension

// we could @ExtendWith(SpringExtension::class) with @SpringBootTest() for this test. We did not to show how to run a non-spring-boot test
@ExtendWith(SpringExtension::class)
@ContextConfiguration(initializers = [FunctionalProductionConfigInitializer::class], classes = [TestApplicationConfiguration::class])
@TestPropertySource(locations = ["classpath:application.properties"])
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
annotation class SpringContextTest

// @ContextConfiguration does not include the initializers, they need to be added manually to all SpringRunningContextTests
// that is unfortunate but otherwise the tests cannot add additional initializers without failing
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = [TestApplicationConfiguration::class])
@TestPropertySource(locations = ["classpath:application.properties"])
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
annotation class SpringRunningContextTest

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
    NoCassandraAnnotationConfig::class,

    AnnotationGreeterConfig::class
])
open class TestApplicationConfiguration
package de.richargh.springkotlinhexagonal.config

import de.richargh.springkotlinhexagonal.web.HomeController
import de.richargh.springkotlinhexagonal.domain.Replier
import de.richargh.springkotlinhexagonal.web.FunctionalHomeRoutes
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.HandlerStrategies
import org.springframework.web.reactive.function.server.RouterFunctions
import org.thymeleaf.spring5.view.reactive.ThymeleafReactiveViewResolver

fun functionalHomeConfig() = beans {
    bean<Replier>()
    bean<HomeController>()
    bean<FunctionalHomeRoutes>()
    bean("webHandler") {
        RouterFunctions.toWebHandler(ref<FunctionalHomeRoutes>().router(), HandlerStrategies.builder().viewResolver(ref()).build())
    }

    bean {
        val prefix = "classpath:/templates/"
        ThymeleafReactiveViewResolver()
    }

    environment({!activeProfiles.contains("foo")}){
        bean {

        }
    }

    profile("foo") {
        bean { }
    }
}
package de.richargh.springkotlinhexagonal

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.context.support.beans
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.servlet.ViewResolver
import org.thymeleaf.ITemplateEngine
import org.thymeleaf.spring5.ISpringTemplateEngine
import org.thymeleaf.spring5.SpringTemplateEngine
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.spring5.view.ThymeleafViewResolver
import org.thymeleaf.templatemode.TemplateMode
import reactor.core.publisher.Mono

fun beans() = beans {

    bean<TomcatServletWebServerFactory>()

//    bean<ITemplateEngine>{
//        val templateResolver = SpringResourceTemplateResolver()
////            templateResolver.setApplicationContext(this)
//        templateResolver.templateMode = TemplateMode.HTML
//        templateResolver.prefix = "views/"
//        templateResolver.suffix = ".html"
//
//        val engine = SpringTemplateEngine()
//        engine.enableSpringELCompiler = true
//        engine.setTemplateResolver(templateResolver)
//        engine
//    }

//    bean<ViewResolver>{
//        val viewResolver = ThymeleafViewResolver()
//        viewResolver.templateEngine = ref()
//        viewResolver.characterEncoding = "UTF-8"
//        viewResolver
//    }

    bean<Foo>()

    bean<HomeController>()

//    bean<RouterFunction<ServerResponse>>{
//        route(RequestPredicates.GET("/posts"), HandlerFunction { hello() })
//    }

}

//fun hello(): Mono<ServerResponse> {
//    return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
//            .body(BodyInserters.fromObject("Hello, Spring Webflux Example!"))
//}

class Foo{
    fun sayHello() = "Hi"
}
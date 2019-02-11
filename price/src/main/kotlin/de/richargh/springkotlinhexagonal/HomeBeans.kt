package de.richargh.springkotlinhexagonal

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.context.support.beans
import org.springframework.http.MediaType
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.*

fun homeBeans() = beans {

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

    bean {
        router {
            GET("/route") {
                ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(fromObject("Hello World")) }
            ("/blog" and accept(TEXT_HTML)).nest {
                GET("/") { ServerResponse.ok().body(fromObject("Foo")) }
            }
        }
    }

    bean<Greeter>()

    bean<HomeController>()

//    bean<RouterFunction<ServerResponse>>{
//        route(RequestPredicates.GET("/posts"), HandlerFunction { hello() })
//    }

}

//fun hello(): Mono<ServerResponse> {
//    return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
//            .body(BodyInserters.fromObject("Hello, Spring Webflux Example!"))
//}
package de.richargh.springkotlinhexagonal

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.http.MediaType
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.*

@EnableAutoConfiguration
open class Application

fun main(args: Array<String>) {
    val applicationContext = SpringApplicationBuilder()
            .initializers(homeBeans())
            .sources(Application::class.java)
            .run(*args)

    val bean1 = applicationContext.getBean(Foo::class.java)
    println(bean1.sayHello())
}

fun routeHtml() = router {
    accept(TEXT_HTML).nest {
        GET("/") { ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromObject("Hello, Spring Webflux Example!")) }
    }
}
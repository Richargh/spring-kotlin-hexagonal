package de.richargh.springkotlinhexagonal

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.context.support.beans
import org.springframework.http.MediaType
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.*

fun homeBeans() = beans {

    bean<TomcatServletWebServerFactory>()

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
}
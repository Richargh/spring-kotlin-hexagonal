package de.richargh.springkotlinhexagonal.web

import de.richargh.springkotlinhexagonal.domain.Greeter
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RequestPredicates.accept
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.router

class FunctionalHomeRoutes(private val greeter: Greeter) {

    fun router() = router {
        accept(TEXT_HTML).nest {
            GET("/foo") { ok().render("greeting") }
//            GET("/users", userHandler::findAllView)
        }
    }
}
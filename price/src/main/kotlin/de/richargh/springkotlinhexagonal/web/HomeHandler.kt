package de.richargh.springkotlinhexagonal.web

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok

class HomeHandler {
    fun greet(req: ServerRequest) =
            ok().render("greeting", mapOf("message" to "bla"))

}
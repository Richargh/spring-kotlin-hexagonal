package de.richargh.springkotlinhexagonal

import java.util.HashMap

import io.javalin.Javalin

object Main {

    private val reservations = mutableMapOf(
            "saturday" to "No reservation",
            "sunday" to "No reservation"
    )

    @JvmStatic
    fun main(args: Array<String>) {

        val app = Javalin.create()
                .enableStaticFiles("/public")
                .start(7777)

        app.get("/") { ctx ->
            ctx.html("Foo")
        }

    }

}
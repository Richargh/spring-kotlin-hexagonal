package de.richargh.springkotlinhexagonal

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get

fun main(args: Array<String>) {
    JavalinApp(7777).init()
}


class JavalinApp(private val port: Int) {

    fun init(): Javalin {

        val app = Javalin.create().apply {
            enableStaticFiles("/public")
            port(port)
            exception(Exception::class.java) { e, _ -> e.printStackTrace() }
        }.start()

        app.routes {
            get("/") { ctx ->
                ctx.html("Foo")
            }
        }

        return app
    }
}
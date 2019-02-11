package de.richargh.springkotlinhexagonal

import io.javalin.Javalin
import org.junit.jupiter.api.Assertions.assertEquals

class TestIntegration() {

    private lateinit var app: Javalin
    private val url = "http://localhost:8000/"

    fun setUp() {
        app = JavalinApp(8000).init()
    }

    fun tearDown() {
        app.stop()
    }

    fun testDummy() {
        assertEquals(1, 1)
    }
}
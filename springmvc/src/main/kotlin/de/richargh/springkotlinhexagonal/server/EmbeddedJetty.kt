package de.richargh.springkotlinhexagonal.server

import java.io.IOException

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.web.context.ContextLoaderListener
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.servlet.DispatcherServlet


private val PORT = 9290

fun main(args: Array<String>) {
    EmbeddedJetty().startJetty(PORT)
}

class EmbeddedJetty {

    @Throws(Exception::class)
    fun startJetty(port: Int) {
        LOGGER.debug("Starting server at port {}", port)
        val server = Server(port)

        server.handler = servletContextHandler()

        addRuntimeShutdownHook(server)

        server.start()
        LOGGER.info("Server started at port {}", port)
        server.join()
    }

    private val LOGGER = LoggerFactory.getLogger(EmbeddedJetty::class.java)


    private val CONTEXT_PATH = "/"
    private val CONFIG_LOCATION_PACKAGE = "de.richargh.springkotlinhexagonal.config"
    private val MAPPING_URL = "/"
    private val WEBAPP_DIRECTORY = "webapp"


    private fun servletContextHandler(): ServletContextHandler {
        val contextHandler = ServletContextHandler(ServletContextHandler.SESSIONS)
        contextHandler.errorHandler = null

        contextHandler.resourceBase = ClassPathResource(WEBAPP_DIRECTORY).uri.toString()
        contextHandler.contextPath = CONTEXT_PATH
        val webAppContext = webApplicationContext()
        val dispatcherServlet = DispatcherServlet(webAppContext)
        val springServletHolder = ServletHolder("mvc-dispatcher", dispatcherServlet)
        contextHandler.addServlet(springServletHolder, MAPPING_URL)
        contextHandler.addEventListener(ContextLoaderListener(webAppContext))

        return contextHandler
    }

    private fun webApplicationContext(): WebApplicationContext {
        val context = AnnotationConfigWebApplicationContext()
        context.setConfigLocation(CONFIG_LOCATION_PACKAGE)
        return context
    }

    private fun addRuntimeShutdownHook(server: Server) {
        Runtime.getRuntime().addShutdownHook(Thread(Runnable {
            if (server.isStarted) {
                server.stopAtShutdown = true
                try {
                    server.stop()
                } catch (e: Exception) {
                    println("Error while stopping jetty server: " + e.message)
                    LOGGER.error("Error while stopping jetty server: " + e.message, e)
                }
            }
        }))
    }
}

package de.richargh.springkotlinhexagonal

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.web.context.ContextLoaderListener
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.context.support.GenericWebApplicationContext
import org.springframework.web.servlet.DispatcherServlet
import java.io.IOException
import java.net.URI

private val PORT = 9290
fun main(args: Array<String>) {
    val webAppContext = AnnotationConfigWebApplicationContext()
    webAppContext.register(ApplicationWebConfig::class.java)



    EmbeddedJetty().startJetty(PORT, webAppContext)
}

class EmbeddedJetty {

    @Throws(Exception::class)
    fun startJetty(port: Int, webAppContext: AnnotationConfigWebApplicationContext) {
        LOGGER.debug("Starting server at port {}", port)
        val server = Server(port)

        server.handler = servletContextHandler(webAppContext)
//        webAppContext.refresh()
//        println(webAppContext.getBean(HomeController::class.java))

        addRuntimeShutdownHook(server)

        server.start()
        LOGGER.info("Server started at port {}", port)
        server.join()
    }


    private val LOGGER = LoggerFactory.getLogger(EmbeddedJetty::class.java)


    private val CONTEXT_PATH = "/"
    private val CONFIG_LOCATION_PACKAGE = "com.fernandospr.example.config"

    val MVC_SERVLET_NAME = "mvcDispatcher"
    private val MAPPING_URL = "/"
    private val WEBAPP_DIRECTORY = "webapp"


    private fun servletContextHandler(webAppContext: AnnotationConfigWebApplicationContext): ServletContextHandler {
        val contextHandler = ServletContextHandler(ServletContextHandler.SESSIONS)
        contextHandler.errorHandler = null

        val uri = URI("classpath://templates")
        contextHandler.resourceBase = uri.toString()
        contextHandler.contextPath = CONTEXT_PATH
        contextHandler.classLoader = Thread.currentThread().contextClassLoader
        //            contextHandler.addServlet(JspServlet::class.java, "*.jsp")

//        val webAppContext = webApplicationContext()
        val dispatcherServlet = DispatcherServlet(webAppContext) //webAppContext
        val springServletHolder = ServletHolder(dispatcherServlet)


        contextHandler.addServlet(springServletHolder, "/")
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
package de.richargh.springkotlinhexagonal

import org.eclipse.jetty.http.HttpStatus
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.eclipse.jetty.webapp.WebAppContext



fun main(args: Array<String>) {
    val _ctx = WebAppContext()

    val server = Server(7070)
    val handler = ServletContextHandler(server, "/")
    handler.addServlet(ExampleServlet::class.java, "/")
    server.stopAtShutdown = true

    server.start()
}

class ExampleServlet: HttpServlet() {

    @Throws(ServletException::class, IOException::class)
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.status = HttpStatus.OK_200
        resp.writer.println("EmbeddedJetty")
    }
}
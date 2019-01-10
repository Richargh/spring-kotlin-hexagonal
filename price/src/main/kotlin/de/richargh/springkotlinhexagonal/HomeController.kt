package de.richargh.springkotlinhexagonal

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping


@Controller
class HomeController {

    @RequestMapping("/")
    fun index(model: MutableMap<String, Any>): String {
        model["message"] = "Hello World"
        return "greeting"
    }

}
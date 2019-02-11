package de.richargh.springkotlinhexagonal

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping


@Controller
class HomeController(private val foo: Foo) {

    @RequestMapping("/")
    fun index(model: MutableMap<String, Any>): String {
        model["message"] = foo.sayHello()
        return "greeting"
    }

}
package de.richargh.springkotlinhexagonal

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class HomeController{

    @RequestMapping("/")
    fun home(): String{
        return "Home"
    }
}
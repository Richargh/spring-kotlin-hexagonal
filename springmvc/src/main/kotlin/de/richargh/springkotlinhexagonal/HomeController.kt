package de.richargh.springkotlinhexagonal

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class HomeController{

    @RequestMapping("/")
    fun home(): ModelAndView{
        val mav = ModelAndView("Home")
        mav.addObject("name", "Foo")
        return mav
    }
}
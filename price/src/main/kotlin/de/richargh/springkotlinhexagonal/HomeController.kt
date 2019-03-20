package de.richargh.springkotlinhexagonal

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView

@Controller
class HomeController(private val greeter: Greeter, private val speaker: Speaker) {

    @RequestMapping("/{name}")
    fun index(@RequestParam("name")name: String?): ModelAndView {
        return if(name == null){
            createGreeting()
        }
        else {
            createSaying(name)
        }
    }

    private fun createSaying(name: String): ModelAndView {
        val mav = ModelAndView("speaking")
        mav.addObject("message", speaker.speak(name))
        return mav
    }

    private fun createGreeting(): ModelAndView {
        val mav = ModelAndView("greeting")
        mav.addObject("message", greeter.sayHello())
        return mav
    }

}
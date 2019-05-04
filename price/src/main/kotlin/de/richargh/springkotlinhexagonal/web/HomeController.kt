package de.richargh.springkotlinhexagonal.web

import de.richargh.springkotlinhexagonal.domain.Greeter
import de.richargh.springkotlinhexagonal.domain.Replier
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView

@Controller
class HomeController(private val greeter: Greeter, private val replier: Replier) {

    @GetMapping
    fun index(): ModelAndView {
        val mav = ModelAndView("greeting")
        mav.addObject("message", greeter.sayHello())
        return mav
    }

    @GetMapping("reply")
    fun reply(@RequestParam("name") name: String): ModelAndView {
        val mav = ModelAndView("reply")
        mav.addObject("message", replier.speak(name))
        return mav
    }
}
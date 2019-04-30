package de.richargh.springkotlinhexagonal

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView

@Controller
class HomeController(private val greeter: Greeter, private val replier: Replier) {

    @GetMapping
    fun index(): ModelAndView {
        return createGreeting()
    }

    @GetMapping("reply")
    fun reply(@RequestParam("name") name: String): ModelAndView {
        return createReply(name)
    }

    private fun createGreeting(): ModelAndView {
        val mav = ModelAndView("greeting")
        mav.addObject("message", greeter.sayHello())
        return mav
    }

    private fun createReply(name: String): ModelAndView {
        val mav = ModelAndView("reply")
        mav.addObject("message", replier.speak(name))
        return mav
    }
}
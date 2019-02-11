package de.richargh.springkotlinhexagonal

import org.springframework.context.support.beans

fun homeBeans() = beans {
    bean<Greeter>()
    bean<HomeController>()
}
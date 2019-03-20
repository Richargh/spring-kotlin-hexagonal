package de.richargh.springkotlinhexagonal.config

import de.richargh.springkotlinhexagonal.Greeter
import de.richargh.springkotlinhexagonal.HomeController
import org.springframework.context.support.beans

fun homeBeans() = beans {
    bean<Greeter>()
    bean<HomeController>()
    environment({!activeProfiles.contains("foo")}){
        bean {

        }
    }

    profile("foo") {
        bean { }
    }
}
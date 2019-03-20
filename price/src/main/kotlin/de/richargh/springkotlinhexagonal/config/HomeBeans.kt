package de.richargh.springkotlinhexagonal.config

import de.richargh.springkotlinhexagonal.Greeter
import de.richargh.springkotlinhexagonal.HomeController
import de.richargh.springkotlinhexagonal.Speaker
import org.springframework.context.support.beans

fun homeBeans() = beans {
    bean<Speaker>()
    bean<HomeController>()
    environment({!activeProfiles.contains("foo")}){
        bean {

        }
    }

    profile("foo") {
        bean { }
    }
}
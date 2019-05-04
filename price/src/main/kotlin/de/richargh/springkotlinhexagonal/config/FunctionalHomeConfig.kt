package de.richargh.springkotlinhexagonal.config

import de.richargh.springkotlinhexagonal.web.HomeController
import de.richargh.springkotlinhexagonal.domain.Replier
import org.springframework.context.support.beans

fun functionalHomeConfig() = beans {
    bean<Replier>()
    bean<HomeController>()
    environment({!activeProfiles.contains("foo")}){
        bean {

        }
    }

    profile("foo") {
        bean { }
    }
}
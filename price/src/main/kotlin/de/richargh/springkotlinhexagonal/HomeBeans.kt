package de.richargh.springkotlinhexagonal

import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
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
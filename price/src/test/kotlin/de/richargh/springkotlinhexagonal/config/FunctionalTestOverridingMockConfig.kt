package de.richargh.springkotlinhexagonal.config

import de.richargh.springkotlinhexagonal.Greeter
import io.mockk.every
import io.mockk.mockk
import org.springframework.context.support.beans

fun functionalTestOverridingMockConfig() = beans {
    bean<Greeter>(isPrimary = true){
        val greeter = mockk<Greeter>(relaxed = true)
        every { greeter.sayHello() } returns "Moin"
        greeter
    }
}
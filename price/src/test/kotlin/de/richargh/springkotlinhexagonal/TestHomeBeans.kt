package de.richargh.springkotlinhexagonal

import io.mockk.every
import io.mockk.mockk
import org.springframework.context.support.beans

fun testHomeBeans() = beans {
    bean<Greeter>(isPrimary = true){
        val foo = mockk<Greeter>(relaxed = true)
        every { foo.sayHello() } returns "Moin"
        foo
    }
}
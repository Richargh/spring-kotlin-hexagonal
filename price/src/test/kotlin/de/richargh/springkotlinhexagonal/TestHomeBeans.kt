package de.richargh.springkotlinhexagonal

import io.mockk.every
import io.mockk.mockk
import org.springframework.context.support.beans

fun testHomeBeans() = beans {
    bean<Foo>(isPrimary = true){
        val foo = mockk<Foo>(relaxed = true)
        every { foo.sayHello() } returns "Moin"
        foo
    }
}
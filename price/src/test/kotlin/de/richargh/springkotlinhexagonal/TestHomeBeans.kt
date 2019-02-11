package de.richargh.springkotlinhexagonal

import io.mockk.mockk
import org.springframework.context.support.beans

fun testHomeBeans() = beans {
    bean<Foo>{ mockk() }
}
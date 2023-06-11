package org.pwgtil.firstApp

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.*
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicInteger


@Configuration
class AppConfig {
    @Bean
    //@Scope("singleton") //or @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    @Scope("prototype") //or @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    fun createCounter(): AtomicInteger {
        return AtomicInteger()
    }
}

@Component
class AppRunner(private val counter1: AtomicInteger, private val counter2: AtomicInteger): CommandLineRunner {
    override fun run(vararg args: String?) {
        counter1.addAndGet(2)
        counter2.addAndGet(3)
        counter1.addAndGet(5)
        println(counter1.get())
        println(counter2.get())
    }
}
package org.pwgtil.firstApp

import org.springframework.boot.CommandLineRunner
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationListener
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

class HelloEvent(source: Any?, val message: String) : ApplicationEvent(source!!) //This inheritance is not MANDATORY!!

@Component
class HelloListenerOne : ApplicationListener<HelloEvent> {
    override fun onApplicationEvent(event: HelloEvent) {
        "First listener began to handle the event".let(::println)
        try {
            Thread.sleep(100)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
        "First listener handled the event, the message is ${event.message}".let(::println)
    }
}

@Component
class HelloListenerTwo : ApplicationListener<HelloEvent> {
    override fun onApplicationEvent(event: HelloEvent) {
        "Second listener began to handle the event".let(::println)
        try {
            Thread.sleep(100)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
        "Second listener handled the event, the message is ${event.message}".let(::println)
    }
}

@Component
class AnnotationListener {
    @EventListener
    fun handleEvent(event: HelloEvent) {
        "Annotation listener handled the event, the message is ${event.message}".let(::println)
    }
}

@Component
class RunnerEL(private val eventPublisher: ApplicationEventPublisher) : CommandLineRunner {

    override fun run(vararg args: String?) {
        "The application was started".let(::println)
        val event: HelloEvent = HelloEvent(this, "Hello World")
        eventPublisher.publishEvent(event)
        eventPublisher.publishEvent(PojoEvent())
        Thread.sleep(100)
        eventPublisher.publishEvent(event)
        "The application was ended".let(::println)
    }

}

class PojoEvent

@Component
class PojoAnnotationListener {
    @EventListener
    fun startStuff(event: PojoEvent) {
        "Here goes the POJO!!".let(::println)
    }
}
package org.pwgtil.firstApp

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

class Person(private val name: String)

@Component
open class Book

@Component
open class Movie

@ComponentScan("org.pwgtil.firstApp")
@Configuration
open class Config {

    @Bean
    open fun personMary() = Person("Mary")

}

package org.pwgtil.firstApp

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.stereotype.Component
import java.lang.StringBuilder
import java.util.Random


@Component
class PasswordGenerator {
    companion object {
        private const val CHARACTERS = "aąbcćdeęfghijklłmnńoópqrsśtuvwxyzżź"
        private val random = Random()
    }

    fun generate(length: Int): String {
        val result = StringBuilder()
        for (i in 0 until length) {
            val index = random.nextInt(CHARACTERS.length)
            result.append(CHARACTERS[index])
        }
        return result.toString()
    }
}

@Component
class Runner @Autowired constructor(private val generator: PasswordGenerator, private val person: Person) : CommandLineRunner {
    override fun run(vararg args: String?) {
        println("A short password: " + generator.generate(5))
        println("A long password: " + generator.generate(10))

        var context = AnnotationConfigApplicationContext(Config::class.java)
        println(context.beanDefinitionNames.contentToString())
        context.getBean("personMary", Person::class.java)
        context = AnnotationConfigApplicationContext(PasswordGenerator::class.java)
        println(context.beanDefinitionNames.contentToString())

        println(person.toString())
    }
}
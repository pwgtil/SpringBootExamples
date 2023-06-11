package org.pwgtil.firstApp

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.lang.StringBuilder
import java.util.*

@Configuration
class PasswordConfig {
    companion object {
        private const val ALPHA = "abcdefghijklmnopqrstuvwxyz"
        private const val NUMERIC = "0123456789"
        private const val SPECIAL_CHARS = "!@#$%^&*_=+-/"
    }

    @Bean
    fun allCharacters(): PasswordAlphabet {
        return PasswordAlphabet(ALPHA + NUMERIC + SPECIAL_CHARS)
    }

    class PasswordAlphabet(val characters: String)
}

@Component
class PasswordGeneratorV2(@Autowired private val alphabet: PasswordConfig.PasswordAlphabet) {
    companion object {
        private val random = Random()
    }

    fun generate(length: Int): String {
        val allCharacters = alphabet.characters
        val result = StringBuilder()
        for (i in 0 until length) {
            val index = random.nextInt(allCharacters.length)
            result.append(allCharacters[index])
        }
        return result.toString()
    }
}

@Service
class RunnerV2(private val generator: PasswordGeneratorV2, var applicationContext: ApplicationContext) : CommandLineRunner {
    override fun run(vararg args: String?) {
        println("A short password: " + generator.generate(5))
        println("A long password: " + generator.generate(10))
        applicationContext.getBean(PasswordGeneratorV2::class.java).generate(60).let(::println)

    }
}
package org.pwgtil.firstApp

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

//Alternative (longer one)
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [ConfigT::class])
internal class FirstApplicationSecondTests(@Autowired private val applicationContext: ApplicationContext) {
    @Test
    fun contextLoads() {
        assertThat(applicationContext).isNotNull
        assertThat(applicationContext.beanDefinitionNames).contains("customBean", "configT")
    }
}


//Short version below
@SpringJUnitConfig(ConfigT::class)
internal class FirstApplicationTests(@Autowired private val customBean: String, @Autowired private val applicationContext: ApplicationContext) {
    @Test
    fun contextLoads() {
        assertThat(customBean).isEqualTo("custom bean")
        assertThat(applicationContext).isNotNull
        assertThat(applicationContext.beanDefinitionNames).contains("customBean", "configT")
    }
}

@SpringBootTest
class ApplicationTests(@Autowired private val applicationContext: ApplicationContext) {
    @Test
    fun contextLoads() {
        assertThat(applicationContext.beanDefinitionCount).isGreaterThan(10)
    }
}

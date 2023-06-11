package org.pwgtil.firstApp

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext

@SpringBootTest
class PizzeriaServiceTest(
    @Autowired private val pizzeriaService: PizzeriaService,
    @Autowired private val applicationContext: ApplicationContext
) {
    @Test
    fun orderPizza() {
        println(
            "PizzeriaService context hash code: " +
                    System.identityHashCode(applicationContext)
        )
        assertThat(pizzeriaService.orderPizza("pepperoni")).isNull()
    }
}

@SpringBootTest
class CafeServiceTest(
    @Autowired private val cafeService: CafeService,
    @Autowired private val applicationContext: ApplicationContext
) {
    @Test
    fun orderFood() {
        println(
            "CafeService context hash code: " +
                    System.identityHashCode(applicationContext)
        )
        assertThat(cafeService.orderFood("apple pie")).isNotNull
    }
}

@SpringBootTest
class AllServiceTest(
    @Autowired private val cafeService: CafeService,
    @Autowired private val pizzeriaService: PizzeriaService,
    @Autowired private val applicationContext: ApplicationContext
) {
    @Test
    fun orderFood() {
        println(
            "CafeService context hash code: " +
                    System.identityHashCode(applicationContext)
        )
        assertThat(cafeService.orderFood("apple pie")).isNotNull
    }

    @Test
    fun orderPizza() {
        println(
            "PizzeriaService context hash code: " +
                    System.identityHashCode(applicationContext)
        )
        assertThat(pizzeriaService.orderPizza("pepperoni")).isNull()
    }
}
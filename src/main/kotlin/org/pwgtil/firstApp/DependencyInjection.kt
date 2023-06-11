package org.pwgtil.firstApp

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.CommandLineRunner
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service


// Constructor injection (best one) ------------------------------------------------------
@Component
class ProductDI(private var price: String = "million", private var sold: Boolean = false)


@Component
class CustomerDI(@Autowired private val product: ProductDI)


// Type matching: @Primary, @Bean("with name") or direct naming of the bean method in getBean() context method---------
class Car(var name: String, private var model: String) {
    @Qualifier("teslaEngine")
    @Autowired
    val engine: Engine? = null
}

@Configuration
class ConfigDI {

//    @Bean
//    @Primary // Optional setting for a Bean to be taken first in case other Car objects are in AppContext
    @Bean("let's test it")
    fun teslaCar(): Car {
        return Car("Tesla", "2023")
    }

    @Bean
    fun toyotaCar(): Car {
        return Car("Toyota", "2022")
    }

    @Bean
    fun teslaEngine(): Engine{
        return Engine("Tesla", true)
    }

    @Bean
    fun toyotaEngine(): Engine{
        return Engine("Toyota", true)
    }
}


@Service
class RunnerDI(var applicationContext: ApplicationContext) :
    CommandLineRunner {
    override fun run(vararg args: String?) {

        // Create the Spring application context
        val context = AnnotationConfigApplicationContext(ConfigDI::class.java)
        // Retrieve an instance of MyBean
        val myBean = context.getBean("let's test it", Car::class.java)
//        val myBean = context.getBean("toyotaCar", Car::class.java)
//        val myBean = context.getBean(Car::class.java)
        println(myBean.name)
        println(myBean.engine?.brand)

    }
}


// @Qualifier---------------------------------------------------------------------------------------

class Engine(val brand: String, val isRunning: Boolean)


package org.pwgtil.firstApp

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import java.util.*


// Component init/destroy with annotations --------------------------------------------------------------------
@Component
class TechLibraryComponent {
    private val bookTitles = Collections.synchronizedList(ArrayList<String>())

    @PostConstruct
    fun init() {
        bookTitles.add("Clean Code")
        bookTitles.add("The Art of Computer Programming")
        bookTitles.add("Introduction to Algorithms")
        println("The library has been initialized: $bookTitles (@Component example)")
    }

    @PreDestroy
    fun destroy() {
        bookTitles.clear()
        println("The library has been cleaned: $bookTitles (@Component example)")
    }

}

// Bean init/destroy with annotations -------------------------------------------------------------------------
@Configuration
class ConfigBeanLifecycle {

    @Bean(initMethod = "init", destroyMethod = "destroy")
    fun library(): TechLibraryBean {
        return TechLibraryBean()
    }
}

class TechLibraryBean {
    private val bookTitles: MutableList<String> = Collections.synchronizedList(ArrayList())

    fun init() {
        bookTitles.add("Clean Code")
        bookTitles.add("The Art of Computer Programming")
        bookTitles.add("Introduction to Algorithms")
        println("The library has been initialized: $bookTitles (@Bean example)")
    }

    fun destroy() {
        bookTitles.clear()
        println("The library has been cleaned: $bookTitles (@Bean example)")
    }
}

// Component init/destroy with interface ---------------------------------------------------------------------
@Component
class TechLibraryComponentInterface : InitializingBean, DisposableBean {
    private val bookTitles: MutableList<String> = Collections.synchronizedList(ArrayList())
    override fun afterPropertiesSet() {
        bookTitles.add("Clean Code")
        bookTitles.add("The Art of Computer Programming")
        bookTitles.add("Introduction to Algorithms")
        println("The library has been initialized: $bookTitles (@Component example with interface)")
    }

    override fun destroy() {
        bookTitles.clear()
        println("The library has been cleaned: $bookTitles (@Component example with interface)")
    }
}

// Post-processor for beans  ---------------------------------------------------------------------------
//// this generates a lot of txt so we'll just comment it. Seems all beans in the scope are going through this
//@Component
//class PostProcessor : BeanPostProcessor {
//    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
//        println("Before initialization: $beanName")
//        return super.postProcessBeforeInitialization(bean, beanName)
//    }
//
//    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
//        println("After initialization: $beanName")
//        return super.postProcessAfterInitialization(bean, beanName)
//    }
//}
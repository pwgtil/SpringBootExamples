package org.pwgtil.firstApp

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Configuration
class ConfigT {
    @Bean
    fun customBean(): String {
        return "custom bean"
    }
}

@Component
class PizzaMenu {
    private val pizzas = listOf("margherita", "mushrooms and vegetables")
    fun isOnMenu(name: String): Boolean {
        return pizzas.contains(name)
    }
}

@Component
class DessertMenu {
    private val desserts = listOf("apple pie", "almond cake")
    fun isOnMenu(name: String): Boolean {
        return desserts.contains(name)
    }
}

@Component
class PizzeriaService (@Autowired private val pizzaMenu: PizzaMenu) {
    fun orderPizza(name: String?): String? {
        return if (pizzaMenu.isOnMenu(name!!)) {
            println("Thanks for the order! Your pizza will be ready in 15 minutes")
            name
        } else {
            println("We don't have such pizza on our menu")
            null
        }
    }
}

@Component
class CafeService(@Autowired private val pizzaMenu: PizzaMenu, @Autowired private val dessertMenu: DessertMenu) {
    fun orderFood(name: String): String? {
        return if (pizzaMenu.isOnMenu(name) || dessertMenu.isOnMenu(name)) {
            println("Thanks for the order. Your $name will be ready soon")
            name
        } else {
            println("We don't have such food on our menu")
            null
        }
    }
}

class BookT(
    private var id: Long,
    private var name: String,
    private val author: String,
    private var pageCount: Int
) {
    public fun getPageCount(): Int {
        return pageCount
    }
}

@Component
class BookService {
    fun getBooksByAuthor(author: String): List<BookT> {
        return listOf(
            BookT(1L, "title1", author, 100),
            BookT(2L, "title2", author, 200),
            BookT(3L, "title3", author, 300)
        )
    }
}

@Component
class BookStatisticsService(@Autowired private val bookService: BookService) {
    fun getTotalPagesByAuthor(author: String) : Int {
        return bookService.getBooksByAuthor(author).sumOf { it.getPageCount() }
    }
}


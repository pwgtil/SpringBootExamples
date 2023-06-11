package org.pwgtil.firstApp

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping


data class Pizza(var name: String, var price: Double)

@Controller
class PizzeriaController {
    private val pizzaList: List<Pizza> = listOf(
        Pizza("Margherita", 5.0),
        Pizza("Napoletana", 6.0),
        Pizza("Calzone", 7.5)
    )

    @GetMapping("/home")
    fun home(model: Model): String {
        model.addAttribute("pizzas", pizzaList)
        return "menu"
    }
}

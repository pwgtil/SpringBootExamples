package org.pwgtil.firstApp

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.concurrent.ConcurrentHashMap

@SpringBootApplication
class FirstApplication {

    @Bean
    fun address1(): String {
        return "Blue Street, 102"
    }

    @Bean
    fun address2(): String {
        return "Velvet Street, 66"
    }

    @Bean
    fun customer(@Qualifier("greenStreet") address: String): Customer {
        return Customer("Clara Foster", address)
    }

    @Bean
    fun temporary(@Autowired customer: Customer): Customer {
        println(customer)
        return customer
    }
}

fun main(args: Array<String>) {
    val context = runApplication<FirstApplication>(*args)
    println(context.beanDefinitionNames.contentToString())
}

@Configuration
class Addresses {
    @Bean("greenStreet")
    fun address(): String {
        return "Green Street, 102"
    }
}

@RestController
class AddressController {
    private val addressBook = ConcurrentHashMap<String, String>().apply {
        this["Tom"] = "Morowa 123"
        this["Ania"] = "Bytkowska 123"
    }

    @PostMapping("/addresses")
    fun postAddress(@RequestParam name: String, @RequestParam address: String) {
        addressBook[name] = address
    }

    @DeleteMapping("/addresses")
    fun removeAddress(@RequestParam name: String): String {
        addressBook.remove(name)
        return "$name removed from address book!"
    }

    @GetMapping("addresses/{name}")
    fun getAddress(@PathVariable name: String): String {
        return addressBook[name] ?: "Not found"
    }

    @GetMapping("addresses")
    fun getAddressBook(): ConcurrentHashMap<String, String> {
        return addressBook
    }
}

@RestController
class TaskController {
    val taskList = listOf(
        Task(1, "task1", "A first test task", false),
        Task(2, "task2", "A second test task", true)
    )

    @GetMapping("/tasks/{id}")
    fun getTasks(@PathVariable id: Int): ResponseEntity<Task?> {
        return ResponseEntity(taskList[id - 1], HttpStatus.ACCEPTED)
    }

    @GetMapping("/tasks")
    fun getTasks(): List<Task> {
        return taskList
    }

    @GetMapping(value = ["/greet"], produces = [MediaType.TEXT_HTML_VALUE])
    @ResponseBody
    fun greetUser(): String {
        return "<html><p>Hello and welcome!</p></html>"
    }
}

data class Customer(val name: String, val address: String)

class Task(
    var id: Int,
    var name: String,
    var description: String,
    var completed: Boolean
)

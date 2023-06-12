package org.pwgtil.firstApp

import jakarta.validation.Valid
import jakarta.validation.constraints.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SpecialAgentController {
    @PostMapping("/agent")
    fun validate(@RequestBody @Valid agent: SpecialAgent): ResponseEntity<String> {
        println("Test to console")
        return ResponseEntity.ok("Agent info is valid")
    }
}


data class SpecialAgent(
    @field:NotNull
    var name: String?,
    var surname: String,
    @field:NotNull
    @field:Email
    var email: String,
    @field:Size(min = 1, max = 3) // redundant owing to Pattern annotation
    @field:Pattern(regexp = "[0-9]{1,3}")
    var code: String,
    @field:NotBlank //cannot be null and of length 0 after trimming
    var status: String,
    @field:Min(value = 18, message = "Age must be greater than or equal to 18")
    var age: Int,
    @field:NotEmpty //cannot be null and of length 0. Ok for " " value
    var motto: String,
    @field:Size(min = 0, max = 4)
    var cars: List<String>,
    @field:Max(5)
    var numberOfCurrentMissions: Int
)

// Example correct JSON
//{
//    "name": "James",
//    "surname": "Bond",
//    "email" : "james.bond@mi6.uk",
//    "code": "007",
//    "status": "special agent",
//    "age": 51,
//    "motto": "What's done is done",
//    "cars" : ["audi", "secret"],
//    "numberOfCurrentMissions" : 2
//}
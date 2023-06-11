package org.pwgtil.firstApp

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

class UserInfo(
    val id: Int? = null,
    val phone: String? = null,
    val enabled: Boolean? = null,
    val name: String? = null
)

@RestController
class UserInfoController {
    @PostMapping(value = ["/user"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun userStatus(@RequestBody user: UserInfo): String {
        return if (user.enabled == true) {
            "Hello! ${user.name}. Your account is enabled."
        } else {
            "Hello! Nice to see you, ${user.name}! YOur account is disabled"
        }
    }

    @PostMapping("/users")
    fun userStatus(@RequestBody users: List<UserInfo>): String {
        return "Added ${users.size} users"
    }
}
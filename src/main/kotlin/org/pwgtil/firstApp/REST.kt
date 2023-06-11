package org.pwgtil.firstApp

import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate


@Component
class WordRandomizer {
    private val restTemplate = RestTemplate()
    val randomWord: String
        get() =// Accesses external API to get one random word
            restTemplate.getForObject(
                "https://random-word-api.vercel.app/api?words=1",
                Array<String>::class.java
            )!![0]

    fun get3RandomWords(): MutableList<*>? {
        // Accesses external API to get 3 random words
        return restTemplate.getForObject(
            "https://random-word-api.vercel.app/api?words=3",
            MutableList::class.java
        )
    }
}